import logging
from selenium import webdriver
from selenium.webdriver.common.by import By

# Configurar o logger
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

# Inicialize o driver do Chrome
driver = webdriver.Chrome()

# Contagem de testes bem-sucedidos e mal-sucedidos
num_tests_passed = 0
num_tests_failed = 0

try:
    # Teste 1: Login com usuário e senha válidos
    logging.info("Teste 1: Login com usuário e senha válidos")
    driver.get("http://localhost:8080/InvestiDor/faces/index.xhtml")
    user_input = driver.find_element(By.ID, "input_formLogin:j_idt12")
    user_input.send_keys("admin")
    password_input = driver.find_element(By.ID, "input_formLogin:j_idt14")
    password_input.send_keys("admin")
    login_button = driver.find_element(By.ID, "formLogin:j_idt19")
    login_button.click()
    driver.implicitly_wait(10)
    assert driver.current_url == "http://localhost:8080/InvestiDor/faces/indexResumo.xhtml"
    logging.info("Login bem-sucedido")
    num_tests_passed += 1

    # Teste 2: Logout do sistema
    logging.info("Teste 2: Logout do sistema")

    logout_button = driver.find_element(By.ID, "formMenuUsuario:j_idt13")
    logout_button.click()
    logout_button = driver.find_element(By.ID, "formMenuUsuario:j_idt18")
    logout_button.click()
    driver.implicitly_wait(10)
    assert driver.current_url == "http://localhost:8080/InvestiDor/faces/index.xhtml"
    logging.info("Logout bem-sucedido")
    num_tests_passed += 1

except Exception as e:
    # Tratar exceções e contagem de testes falhos
    logging.error("Erro no teste: " + str(e))
    num_tests_failed += 1

finally:
    # Imprimir resultados do teste
    logging.info("\033[1;32mNúmero de testes bem-sucedidos: " + str(num_tests_passed) + "\033[1;0m")
    if num_tests_failed > 0:
        logging.error("\033[1;31mNúmero de testes mal-sucedidos: " + str(num_tests_failed) + "\033[1;0m")
    else:
        logging.info("\033[1;32mTodos os testes foram bem-sucedidos!\033[1;0m")

    # Fechar o navegador
    driver.quit()