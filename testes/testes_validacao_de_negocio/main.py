
from keys import pegarloginesenha

from testes import login,lougout,irRendaFixa
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

email,senha = pegarloginesenha()
logging.info("Iniciando teste de login...")

validador = login(email,senha,driver)
if validador == 1:
    logging.info("Login bem-sucedido")
    num_tests_passed += 1
else:    
    num_tests_failed += 1

    logging.info("Login falhou!")

logging.info("Iniciando teste de lougout...")
validador = lougout(driver)
if validador == 1:
    logging.info("Lougout bem-sucedido")
    num_tests_passed  += 1

else:    
    logging.info("Lougout falhou!")

    num_tests_failed  += 1


logging.info("\033[1;32mNúmero de testes bem-sucedidos: " + str(num_tests_passed) + "\033[1;0m")
if num_tests_failed > 0:
        logging.error("\033[1;31mNúmero de testes mal-sucedidos: " + str(num_tests_failed) + "\033[1;0m")
else:
        logging.info("\033[1;32mTodos os testes foram bem-sucedidos!\033[1;0m")



validador = irRendaFixa(driver)