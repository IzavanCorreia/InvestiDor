
from keys import pegarloginesenha,criarInvestimento,criarInvestimentoDataInicialMaiorQueAFinal,criarInvestimentoEditar

from testes import login,lougout,irRendaFixa,verificarTeste,criarRendafixa,pegarIdsCards,diferencaSimetrica,editarRendafixa,registrar
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


driver.get("http://localhost:8080/InvestiDor/faces/indexRendaVariavel.xhtml")
logout_button = driver.find_element(By.ID, "formIndexRendaVariavel:criarrendavariavel")
logout_button.click()


"""
driver.get("http://localhost:8080/InvestiDor/faces/admin/indexTicket.xhtml")
driver.implicitly_wait(10)

logout_button = driver.find_element(By.ID, "formTicket:cadastrar_ticket_tESTE")
logout_button.click()
driver.implicitly_wait(10)
driver.implicitly_wait(10)

logout_button = driver.find_element(By.ID, "input_j_idt21:nome_ticket")
logout_button.send_keys("BBDC3")
#driver.get("http://localhost:8080/InvestiDor/faces/index.xhtml")
user_input = driver.find_element(By.ID, "input_j_idt21:nome_empresa")
user_input.send_keys("Bradesco S.A")
password_input = driver.find_element(By.ID, "input_j_idt21:ticket_valor_atual")
password_input.clear()
password_input.send_keys("13.75")

"""

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


logging.info("\033[1;32mNúmero de testes bem-sucedidos: " + str(num_tests_passed) + "\033[1;0m")
if num_tests_failed > 0:
        logging.error("\033[1;31mNúmero de testes mal-sucedidos: " + str(num_tests_failed) + "\033[1;0m")
else:
        logging.info("\033[1;32mTodos os testes foram bem-sucedidos!\033[1;0m")



