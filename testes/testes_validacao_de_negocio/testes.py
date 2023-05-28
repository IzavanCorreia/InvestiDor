from selenium.webdriver.common.by import By
import logging
from selenium.webdriver import Keys

def login(email,password,driver):
    try:
        driver.get("http://localhost:8080/InvestiDor/faces/index.xhtml")
        user_input = driver.find_element(By.ID, "input_formLogin:usuario")
        user_input.send_keys(email)
        password_input = driver.find_element(By.ID, "input_formLogin:senha_password")

        
        password_input.send_keys(password)
        login_button = driver.find_element(By.ID, "formLogin:botaologar")
        login_button.click()
        driver.implicitly_wait(10)
        assert driver.current_url == "http://localhost:8080/InvestiDor/faces/indexResumo.xhtml"
        return 1
    except:
        return 0
def lougout(driver):
    try:
        # Teste 2: Logout do sistema
        driver.get("http://localhost:8080/InvestiDor/faces/indexResumo.xhtml")

        

        logout_button = driver.find_element(By.ID, "formMenuUsuario:menu")
        logout_button.click()
        logout_button = driver.find_element(By.ID, "formMenuUsuario:logout")
        logout_button.click()
        driver.implicitly_wait(10)
        assert driver.current_url == "http://localhost:8080/InvestiDor/faces/index.xhtml"
        return 1
    except:
        return 0


def irRendaFixa(driver):
    driver.get("http://localhost:8080/InvestiDor/faces/indexResumo.xhtml")

    logout_button = driver.find_element(By.ID, "formMenuUsuario:menu")
    logout_button.click()

    logout_button = driver.find_element(By.ID, "formMenuUsuario:rendafixa")
    logout_button.click()

    driver.implicitly_wait(10)
    assert driver.current_url == "http://localhost:8080/InvestiDor/faces/indexRendaFixa.xhtml"
    return 1
    
def verificarTeste(texto,testepassou,testefalhou,retorno):
    if retorno == 1:
        testepassou = testepassou + 1
        logging.info(texto+" passou!")
    if retorno == 0:
        testefalhou = testefalhou + 1
        logging.info(texto+" falhou!")
    return testepassou,testefalhou

def criarRendafixa(driver,investimento):



    logout_button = driver.find_element(By.ID, "formIndexRendaFixa:criarRendaFixa")
    logout_button.click()

    nome_cdb = driver.find_element(By.ID, "input_formCadInvestimento:nome_rendafixa")
    nome_cdb.send_keys(investimento.nome)
    indexador_input = driver.find_element(By.ID, "input_formCadInvestimento:indexador")
    indexador_input.send_keys(investimento.indexador)

    quantidade_input = driver.find_element(By.ID, "input_formCadInvestimento:quantidade")

    quantidade_input.send_keys(Keys.BACK_SPACE)
    quantidade_input.send_keys(Keys.BACK_SPACE)

    quantidade_input.send_keys(investimento.quantidade)

    valor_unitario_input = driver.find_element(By.ID, "input_formCadInvestimento:valor_unit")

    valor_unitario_input.send_keys(Keys.BACK_SPACE)
    valor_unitario_input.send_keys(Keys.BACK_SPACE)


    valor_unitario_input.send_keys(investimento.valor_unit)
    valor_unitario_atual_input = driver.find_element(By.ID, "input_formCadInvestimento:valor_atual")
    valor_unitario_atual_input.send_keys(Keys.BACK_SPACE)
    valor_unitario_atual_input.send_keys(Keys.BACK_SPACE)

    valor_unitario_atual_input.send_keys(investimento.valor_atual)

    data_inicio_input = driver.find_element(By.ID, "formCadInvestimento:data_inicial_input")
    #data_inicio_input.send_keys(str(investimento.data_inicial))

    


    

    data_final_input = driver.find_element(By.ID, "formCadInvestimento:data_final_input")
    data_final_input.send_keys(str(investimento.data_final))

    data_texto = str(investimento.data_inicial)


    data_inicio_input.send_keys(data_texto)

    tipo_input = driver.find_element(By.ID, "input_formCadInvestimento:tipo_investimento")
    tipo_input.send_keys(investimento.tipo_investimento)

    investir_button = driver.find_element(By.ID, "formCadInvestimento:imposto")
    investir_button.click()

    investir_button = driver.find_element(By.ID, "formCadInvestimento:cadastrar_investimento")
    investir_button.click()
    driver.implicitly_wait(10)
    #assert driver.current_url == "http://localhost:8080/InvestiDor/faces/indexRendaFixa.xhtml"
    #logging.info("Renda criado bem-sucedido")

def pegarIdsCards(driver):
    elements = driver.find_elements(by="css selector", value=".ids")
    ids = []
    # Iterar sobre os elementos encontrados
    for element in elements:
        # Fazer algo com cada elemento, por exemplo, imprimir seu texto
        ids.append(element.text)
    return ids