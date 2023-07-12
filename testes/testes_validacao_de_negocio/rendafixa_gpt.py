import unittest
import logging
from selenium import webdriver
from selenium.webdriver.common.by import By
from keys import pegarloginesenha
from testes import login, logout, irRendaFixa, criarRendafixa, pegarIdsCards, diferencaSimetrica, editarRendafixa, registrar

# Configurar o logger
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

class TestesSelenium(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        # Inicializar o driver do Chrome
        cls.driver = webdriver.Chrome()
    
    @classmethod
    def tearDownClass(cls):
        # Fechar o driver do Chrome após os testes
        cls.driver.quit()

    def setUp(self):
        # Configurar o driver antes de cada teste
        self.driver.get("http://localhost:8080/InvestiDor/faces/index.xhtml")

    def test_registro(self):
        email, senha, cpf, nome, sobrenome, telefone = pegarloginesenha()
        registrar_result = registrar(cpf, nome, sobrenome, telefone, email, senha, self.driver)
        self.assertTrue(registrar_result, "Registro falhou.")

    def test_login(self):
        email, senha, _, _, _, _ = pegarloginesenha()
        login_result = login(email, senha, self.driver)
        self.assertTrue(login_result, "Login falhou.")

    def test_criar_renda_fixa(self):
        irRendaFixa(self.driver)
        ids_elementos = pegarIdsCards(self.driver)
        criar_result = criarRendafixa(self.driver, criarInvestimento())
        self.assertTrue(criar_result, "Falha ao criar renda fixa.")
        ids_elementos_depois = pegarIdsCards(self.driver)
        self.assertNotEqual(ids_elementos, ids_elementos_depois, "Renda fixa não foi criada.")

        for id_deletar in diferencaSimetrica(ids_elementos, ids_elementos_depois):
            editar_result = editarRendafixa(self.driver, criarInvestimentoEditar(), id_deletar)
            self.assertTrue(editar_result, f"Falha ao editar renda fixa {id_deletar}.")
            
            self.driver.get("http://localhost:8080/InvestiDor/faces/indexRendaFixa.xhtml")
            investir_button = self.driver.find_element(By.ID, f"formCards:deletar{id_deletar}")
            investir_button.click()
            self.driver.implicitly_wait(10)
            
            investir_button = self.driver.find_element(By.ID, f"formDel:certeza{id_deletar}")
            investir_button.click()
            self.driver.implicitly_wait(10)
            
            verificar_se_deletou = pegarIdsCards(self.driver)
            if ids_elementos == verificar_se_deletou:
                logging.info(f"Id {id_deletar} Deletado!")
            else:
                logging.info(f"Id {id_deletar} não foi Deletado!")
                self.fail(f"Id {id_deletar} não foi Deletado.")
    
    def test_criar_renda_fixa_data_invalida(self):
        irRendaFixa(self.driver)
        ids_elementos = pegarIdsCards(self.driver)
        criar_result = criarRendafixa(self.driver, criarInvestimentoDataInicialMaiorQueAFinal())
        self.assertFalse(criar_result, "Renda fixa com data inválida foi criada.")
        ids_elementos_depois = pegarIdsCards(self.driver)
        self.assertEqual(ids_elementos, ids_elementos_depois, "Renda fixa foi criada mesmo com data inválida.")

    def test_logout(self):
        logout_result = logout(self.driver)
        self.assertTrue(logout_result, "Logout falhou.")

if __name__ == "__main__":
    unittest.main()