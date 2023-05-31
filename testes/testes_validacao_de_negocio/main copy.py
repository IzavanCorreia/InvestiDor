
from keys import pegarloginesenha,criarInvestimento,criarInvestimentoDataInicialMaiorQueAFinal,criarInvestimentoEditar

from testes import login,lougout,irRendaFixa,verificarTeste,criarRendafixa,pegarIdsCards,diferencaSimetrica,editarRendafixa,registrar,pegarIdsCardsRendaVariavel
import logging
from selenium import webdriver
from selenium.webdriver.common.by import By
from keys import pegarloginesenha





# Configurar o logger
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')


num_tests_passed = 0
num_tests_failed = 0
# Inicialize o driver do Chrome
driver = webdriver.Chrome()
email,senha,cpf,nome,sobrenome,telefone = pegarloginesenha()

logging.info("Iniciando teste de login...")
num_tests_passed,num_tests_failed = verificarTeste("Login", num_tests_passed,num_tests_failed ,login(email,senha,driver))


ids_elementos = pegarIdsCardsRendaVariavel(driver)


driver.get("http://localhost:8080/InvestiDor/faces/indexRendaVariavel.xhtml")
logout_button = driver.find_element(By.ID, "formIndexRendaVariavel:criarrendavariavel")
logout_button.click()




driver.get("http://localhost:8080/InvestiDor/faces/indexRendaVariavel.xhtml")
driver.implicitly_wait(10)

logout_button = driver.find_element(By.ID, "formIndexRendaVariavel:criarrendavariavel")
logout_button.click()
driver.implicitly_wait(10)
driver.implicitly_wait(10)


logout_button = driver.find_element(By.ID, "input_formCadInvestimento:valorCompra")
logout_button.clear()

logout_button.send_keys("13.75")

logout_button = driver.find_element(By.ID, "input_formCadInvestimento:quantidade_compra")
logout_button.clear()

logout_button.send_keys("100")


logout_button = driver.find_element(By.ID, "formCadInvestimento:data_compra_input")
logout_button.send_keys("03/05/2023")

logout_button = driver.find_element(By.ID, "formCadInvestimento:inserir_criacao")
logout_button.click()



ids_elementos_depois = pegarIdsCardsRendaVariavel(driver)
if ids_elementos != ids_elementos_depois:
        logging.info("Renda variavel criada bem-sucedido")
        num_tests_passed = num_tests_passed + 1
        for id_deletar in diferencaSimetrica(ids_elementos,ids_elementos_depois):
                """
                logging.info("Editar elemento criado")

                num_tests_passed,num_tests_failed = verificarTeste("Editando Renda Fixa", num_tests_passed,num_tests_failed , editarRendafixa(driver,criarInvestimentoEditar(),id_deletar))
                """


                logging.info("Deletar elemento criado")

                investir_button = driver.find_element(By.ID, "formCards:deletaraqui" + str(id_deletar))
                investir_button.click()
                driver.implicitly_wait(10)
                import time
                time.sleep(10)

                investir_button = driver.find_element(By.ID, "formDel:certezaaqui")
                investir_button.click()
                driver.implicitly_wait(10)
                
               
        verificar_se_deletou = pegarIdsCardsRendaVariavel(driver)
        if ids_elementos == verificar_se_deletou:
                logging.info(f"Id {id_deletar} Deletado!")
                num_tests_passed = num_tests_passed + 1
        else:
                logging.info(f"Id {id_deletar} não foi Deletado!")
                num_tests_failed = num_tests_failed + 1
else:
    logging.info("Renda fixa falhou!")
    num_tests_failed = num_tests_failed + 1


logging.info("\033[1;32mNúmero de testes bem-sucedidos: " + str(num_tests_passed) + "\033[1;0m")
if num_tests_failed > 0:
        logging.error("\033[1;31mNúmero de testes mal-sucedidos: " + str(num_tests_failed) + "\033[1;0m")
else:
        logging.info("\033[1;32mTodos os testes foram bem-sucedidos!\033[1;0m")



