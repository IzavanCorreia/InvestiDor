import logging
from selenium import webdriver
from selenium.webdriver import Keys
from selenium.webdriver.common.by import By
from keys import pegarloginesenha
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

    # Teste 2: Ir para a tela de renda fixa

    logging.info("Teste 2: Ir tela de renda fixa")

    logout_button = driver.find_element(By.ID, "formMenuUsuario:menu")
    logout_button.click()

    logout_button = driver.find_element(By.ID, "formMenuUsuario:rendafixa")
    logout_button.click()

    driver.implicitly_wait(10)
    assert driver.current_url == "http://localhost:8080/InvestiDor/faces/indexRendaFixa.xhtml"
    logging.info("Teste tela renda fixa bem-sucedido")
    num_tests_passed += 1

    # Teste 3: Ir para a tela de renda fixa

    logging.info("Teste 3: Criar renda fixa")

    logout_button = driver.find_element(By.ID, "formIndexRendaFixa:rendafixa")
    logout_button.click()

    nome_cdb = driver.find_element(By.ID, "input_formCadInvestimento:j_idt29")
    nome_cdb.send_keys("CDB BANCO SEGURO 107% CDI")
    indexador_input = driver.find_element(By.ID, "input_formCadInvestimento:j_idt31")
    indexador_input.send_keys("CDI")

    quantidade_input = driver.find_element(By.ID, "input_formCadInvestimento:j_idt33")

    quantidade_input.send_keys(Keys.BACK_SPACE)
    quantidade_input.send_keys(Keys.BACK_SPACE)

    quantidade_input.send_keys("1000")

    valor_unitario_input = driver.find_element(By.ID, "input_formCadInvestimento:j_idt35")

    valor_unitario_input.send_keys(Keys.BACK_SPACE)
    valor_unitario_input.send_keys(Keys.BACK_SPACE)


    valor_unitario_input.send_keys("1000")

    valor_unitario_atual_input = driver.find_element(By.ID, "input_formCadInvestimento:j_idt37")
    valor_unitario_atual_input.send_keys(Keys.BACK_SPACE)
    valor_unitario_atual_input.send_keys(Keys.BACK_SPACE)

    valor_unitario_atual_input.send_keys("1001")

    data_inicio_input = driver.find_element(By.ID, "formCadInvestimento:j_idt39_input")
    data_inicio_input.send_keys("11/05/2023")

    data_final_input = driver.find_element(By.ID, "formCadInvestimento:j_idt41_input")
    data_final_input.send_keys("11/05/2026")

    tipo_input = driver.find_element(By.ID, "input_formCadInvestimento:j_idt43")
    tipo_input.send_keys("cdb")

    investir_button = driver.find_element(By.ID, "formCadInvestimento:j_idt46")
    investir_button.click()
    driver.implicitly_wait(10)
    assert driver.current_url == "http://localhost:8080/InvestiDor/faces/indexRendaFixa.xhtml"
    logging.info("Renda criado bem-sucedido")
    num_tests_passed += 1


    # Teste 4: Logout do sistema
    logging.info("Teste 4: Logout do sistema")

    logout_button = driver.find_element(By.ID, "formMenuUsuario:j_idt13")
    logout_button.click()
    logout_button = driver.find_element(By.ID, "formMenuUsuario:j_idt18")
    logout_button.click()
    driver.implicitly_wait(20)
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