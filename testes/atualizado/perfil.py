

from keys import pegarloginesenha,criarInvestimento,criarInvestimentoDataInicialMaiorQueAFinal,criarInvestimentoEditar

from testes import login,lougout,irPerfil,verificarTeste,criarMeta,pegarIdsCardsMetas,diferencaSimetrica,editarMeta,registrar,editarPerfilNome,editarPerfilSobreNome
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


num_tests_passed,num_tests_failed = verificarTeste("Registro", num_tests_passed,num_tests_failed ,registrar(cpf,nome,sobrenome,telefone,email,senha,driver))

logging.info("Iniciando teste de login...")
num_tests_passed,num_tests_failed = verificarTeste("Login", num_tests_passed,num_tests_failed ,login(email,senha,driver))


logging.info("Indo para perfil...")
num_tests_passed,num_tests_failed = verificarTeste("Indo para perfil", num_tests_passed,num_tests_failed , irPerfil(driver))


logging.info("Editar perfil")

editarPerfilNome(driver,"joão")
num_tests_passed = num_tests_passed + 1
editarPerfilSobreNome(driver,"Sobrenome")
num_tests_passed = num_tests_passed + 1

logging.info("Iniciando teste de lougout...")
num_tests_passed,num_tests_failed = verificarTeste("Lougout", num_tests_passed,num_tests_failed ,lougout(driver))



logging.info("\033[1;32mNúmero de testes bem-sucedidos: " + str(num_tests_passed) + "\033[1;0m")
if num_tests_failed > 0:
        logging.error("\033[1;31mNúmero de testes mal-sucedidos: " + str(num_tests_failed) + "\033[1;0m")
else:
        logging.info("\033[1;32mTodos os testes foram bem-sucedidos!\033[1;0m")
