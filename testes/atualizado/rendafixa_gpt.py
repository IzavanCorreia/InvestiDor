import unittest
import logging
from selenium import webdriver
from selenium.webdriver.common.by import By
from keys import pegarloginesenha, criarInvestimentoDataInicialMaiorQueAFinal, criarInvestimento, criarInvestimentoEditar
from testes import login, logout, irRendaFixa, verificarTeste, criarRendafixa, pegarIdsCards, diferencaSimetrica, editarRendafixa, registrar

class TestCases(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        # Configurar o logger
        logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

        # Inicialize o driver do Chrome
        cls.driver = webdriver.Chrome()
        cls.email, cls.senha, cls.cpf, cls.nome, cls.sobrenome, cls.telefone = pegarloginesenha()

    def test_registro(self):
        logging.info("Iniciando teste de registro...")
        result = registrar(self.cpf, self.nome, self.sobrenome, self.telefone, self.email, self.senha, self.driver)
        self.assertTrue(result)
    
    def test_login(self):
        logging.info("Iniciando teste de login...")

        self.driver.get("http://localhost:8080/InvestiDor/faces/indexResumo.xhtml")

        if self.driver.current_url == "http://localhost:8080/InvestiDor/faces/indexResumo.xhtml":
            self.test_logout()
        result = login(self.email, self.senha, self.driver)
        self.assertTrue(result)
    
    def test_criar_renda_fixa(self):

        self.driver.get("http://localhost:8080/InvestiDor/faces/indexResumo.xhtml")

        if self.driver.current_url != "http://localhost:8080/InvestiDor/faces/indexResumo.xhtml":
            self.test_login()

        logging.info("Indo para renda fixa...")
        irRendaFixa(self.driver)
        logging.info("Criar renda fixa...")
        ids_elementos = pegarIdsCards(self.driver)
        criarRendafixa(self.driver, criarInvestimento())
        ids_elementos_depois = pegarIdsCards(self.driver)
        self.assertNotEqual(ids_elementos, ids_elementos_depois, "Falha ao criar renda fixa")

        for id_deletar in diferencaSimetrica(ids_elementos, ids_elementos_depois):
            logging.info("Editar elemento criado")
            result = editarRendafixa(self.driver, criarInvestimentoEditar(), id_deletar)
            print(result)
            self.assertTrue(result, f"Falha ao editar Renda Fixa do id {id_deletar}")

            logging.info("Deletar elemento criado")
            self.driver.get("http://localhost:8080/InvestiDor/faces/indexRendaFixa.xhtml")
            investir_button = self.driver.find_element(By.ID, "formCards:deletar" + str(id_deletar))
            investir_button.click()
            self.driver.implicitly_wait(10)
            import time
            time.sleep(10)
            investir_button = self.driver.find_element(By.ID, "formDel:certeza" + str(id_deletar))
            investir_button.click()
            self.driver.implicitly_wait(10)
            
            verificar_se_deletou = pegarIdsCards(self.driver)
            if ids_elementos == verificar_se_deletou:
                logging.info(f"Id {id_deletar} Deletado!")
            else:
                logging.info(f"Id {id_deletar} não foi Deletado!")
        
    def test_criar_renda_fixa_data_inicial_menor_que_final(self):
        self.driver.get("http://localhost:8080/InvestiDor/faces/indexResumo.xhtml")

        if self.driver.current_url != "http://localhost:8080/InvestiDor/faces/indexResumo.xhtml":
            self.test_login()

        logging.info("Criar renda fixa com data inicial menor que a data final")
        logging.info("Pegar os ids dos cards")
        ids_elementos = pegarIdsCards(self.driver)
        criarRendafixa(self.driver, criarInvestimentoDataInicialMaiorQueAFinal())
        ids_elementos_depois = pegarIdsCards(self.driver)
        self.assertEqual(ids_elementos, ids_elementos_depois, "Criar renda fixa com data inicial menor que a data final falhou")

    def test_logout(self):
        logging.info("Iniciando teste de logout...")
        result = logout(self.driver)
        self.assertTrue(result)
    
    @classmethod
    def tearDownClass(cls):
        cls.driver.quit()


if __name__ == "__main__":
    unittest.main()
