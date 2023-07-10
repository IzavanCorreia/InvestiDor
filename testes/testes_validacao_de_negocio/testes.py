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
    
def registrar(cpf,nome,sobrenome,telefone,email,password,driver):
    try:
        driver.get("http://localhost:8080/InvestiDor/faces/index.xhtml")
        driver.implicitly_wait(10)

        logout_button = driver.find_element(By.ID, "formLogin:irparamodalregistrar")
        logout_button.click()
        #driver.get("http://localhost:8080/InvestiDor/faces/index.xhtml")
        user_input = driver.find_element(By.ID, "input_formCadPuxador:cpf_registraraa")
        user_input.send_keys(cpf)
        password_input = driver.find_element(By.ID, "input_formCadPuxador:nome_registraraa")
        password_input.send_keys(nome)

        password_input = driver.find_element(By.ID, "input_formCadPuxador:sobrenome_registraraa")
        password_input.send_keys(sobrenome)


        password_input = driver.find_element(By.ID, "input_formCadPuxador:telefone_registraraa")
        password_input.send_keys(telefone)


        password_input = driver.find_element(By.ID, "input_formCadPuxador:email_registraraa")
        password_input.send_keys(email)


        password_input = driver.find_element(By.ID, "input_formCadPuxador:senha_registraraa")
        password_input.send_keys(password)


        password_input = driver.find_element(By.ID, "input_formCadPuxador:senha_registrar")
        password_input.send_keys(password)
        driver.implicitly_wait(10)

        login_button = driver.find_element(By.ID, "formCadPuxador:criarconta_registraraa")
        login_button.click()
        driver.implicitly_wait(10)
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

def irPerfil(driver):
    driver.get("http://localhost:8080/InvestiDor/faces/indexPerfil.xhtml")

    driver.implicitly_wait(10)
    assert driver.current_url == "http://localhost:8080/InvestiDor/faces/indexPerfil.xhtml"
    return 1
def irRendaFixa(driver):
    driver.get("http://localhost:8080/InvestiDor/faces/indexRendaFixa.xhtml")

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

    driver.get("http://localhost:8080/InvestiDor/faces/indexRendaFixa.xhtml")


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
    data_final_input.clear()
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
    driver.get("http://localhost:8080/InvestiDor/faces/indexRendaFixa.xhtml")

    elements = driver.find_elements(by="css selector", value=".ids")
    ids = []
    
    # Iterar sobre os elementos encontrados
    for element in elements:
        # Fazer algo com cada elemento, por exemplo, imprimir seu texto
        ids.append(element.text)
    print(ids)
    return ids

def pegarIdsCardsRendaVariavel(driver):
    driver.get("http://localhost:8080/InvestiDor/faces/indexRendaVariavel.xhtml")

    elements = driver.find_elements(by="css selector", value=".ids")
    ids = []
    
    # Iterar sobre os elementos encontrados
    for element in elements:
        # Fazer algo com cada elemento, por exemplo, imprimir seu texto
        ids.append(element.text)
    print(ids)
    return ids

def diferencaSimetrica(lista1, lista2):
    set1 = set(lista1)
    set2 = set(lista2)
    diff1 = set1 - set2
    diff2 = set2 - set1
    return list(diff1.union(diff2))



def editarRendafixa(driver,investimento,id_editar):
    driver.get("http://localhost:8080/InvestiDor/faces/indexRendaFixa.xhtml")


    investir_button = driver.find_element(By.ID, "formCards:editar" + str(id_editar))
    investir_button.click()


    nome_cdb = driver.find_element(By.ID, "input_formAlt:nomeEditar"+ str(id_editar))
    print(investimento.nome)
    nome_cdb.clear()

    nome_cdb.send_keys(investimento.nome)
    indexador_input = driver.find_element(By.ID, "input_formAlt:indexadorEditar"+ str(id_editar))
    indexador_input.clear()
    indexador_input.send_keys(investimento.indexador)

    quantidade_input = driver.find_element(By.ID, "input_formAlt:quantidadeEditar"+ str(id_editar))

    quantidade_input.clear()

    quantidade_input.send_keys(investimento.quantidade)

    valor_unitario_input = driver.find_element(By.ID, "input_formAlt:valor_unitEditar"+ str(id_editar))

    valor_unitario_input.clear()


    valor_unitario_input.send_keys(investimento.valor_unit)
    valor_unitario_atual_input = driver.find_element(By.ID, "input_formAlt:valor_atualEditar"+ str(id_editar))
    valor_unitario_atual_input.clear()


    valor_unitario_atual_input.send_keys(investimento.valor_atual)

    data_inicio_input = driver.find_element(By.ID, f"formAlt:data_inicialEditar{id_editar}_input")
    #data_inicio_input.send_keys(str(investimento.data_inicial))

    




    data_final_input = driver.find_element(By.ID, f"formAlt:data_finalEditar{id_editar}_input")
    data_final_input.clear()

    data_final_input.send_keys(str(investimento.data_final))

    data_texto = str(investimento.data_inicial)
    data_inicio_input.clear()


    data_inicio_input.send_keys(data_texto)

    tipo_input = driver.find_element(By.ID, "input_formAlt:tipo_investimentoEditar"+ str(id_editar))
    tipo_input.clear()
    tipo_input.send_keys(investimento.tipo_investimento)

    #investir_button = driver.find_element(By.ID, "input_formAlt:impostoEditar"+ str(id_editar))
    #investir_button.click()

    investir_button = driver.find_element(By.ID, "formAlt:botarEditar"+ str(id_editar))
    investir_button.click()
    driver.implicitly_wait(10)
    #assert driver.current_url == "http://localhost:8080/InvestiDor/faces/indexRendaFixa.xhtml"
    #logging.info("Renda criado bem-sucedido")



def editarRendaVariavel(driver,investimento,id_editar):
    driver.get("http://localhost:8080/InvestiDor/faces/indexRendaVariavel.xhtml")


    investir_button = driver.find_element(By.ID, "formCards:editaraqui" + str(id_editar))
    investir_button.click()


    nome_cdb = driver.find_element(By.ID, "input_formAlt:valor_compra"+ str(id_editar))
    nome_cdb.clear()

    nome_cdb.send_keys("15")
    indexador_input = driver.find_element(By.ID, "input_formAlt:valor_quantidade"+ str(id_editar))
    indexador_input.clear()
    indexador_input.send_keys("200")


    investir_button = driver.find_element(By.ID, "formAlt:editar_variavel"+ str(id_editar))
    investir_button.click()
    driver.implicitly_wait(10)
    #assert driver.current_url == "http://localhost:8080/InvestiDor/faces/indexRendaFixa.xhtml"
    #logging.info("Renda criado bem-sucedido")



def pegarIdsCardsMetas(driver):
    driver.get("http://localhost:8080/InvestiDor/faces/indexPerfil.xhtml")

    elements = driver.find_elements(by="css selector", value=".ids")
    ids = []
    
    # Iterar sobre os elementos encontrados
    for element in elements:
        # Fazer algo com cada elemento, por exemplo, imprimir seu texto
        ids.append(element.text)
    print(ids)
    return ids



def criarMeta(driver,nome,valor):

    driver.get("http://localhost:8080/InvestiDor/faces/indexPerfil.xhtml")


    logout_button = driver.find_element(By.ID, "perfil:botaometas")
    logout_button.click()
    driver.implicitly_wait(10)
    import time
    time.sleep(4)
    nome_cdb = driver.find_element(By.ID, "input_formMetas:nome_meta")
    nome_cdb.send_keys(nome)
    indexador_input = driver.find_element(By.ID, "input_formMetas:valor_meta")
    indexador_input.clear()
    indexador_input.send_keys(valor)

    

    driver.implicitly_wait(10)


    investir_button = driver.find_element(By.ID, "formMetas:criar_metas")
    investir_button.click()

    driver.implicitly_wait(10)
    #assert driver.current_url == "http://localhost:8080/InvestiDor/faces/indexRendaFixa.xhtml"
    #logging.info("Renda criado bem-sucedido")


def editarMeta(driver,nome,valor,id_editar):
    driver.get("http://localhost:8080/InvestiDor/faces/indexPerfil.xhtml")


    investir_button = driver.find_element(By.ID, "formCards:editar" + str(id_editar))
    investir_button.click()
    import time
    time.sleep(5)


    nome_cdb = driver.find_element(By.ID, "input_formAlterar:nome_meta_editar")
    nome_cdb.clear()

    nome_cdb.send_keys(nome)
    indexador_input = driver.find_element(By.ID, "input_formAlterar:meta_valor_editar")
    indexador_input.clear()
    indexador_input.send_keys(valor)


    investir_button = driver.find_element(By.ID, "formAlterar:alterarbotaometa")
    investir_button.click()
    driver.implicitly_wait(10)
    assert driver.current_url == "http://localhost:8080/InvestiDor/faces/indexPerfil.xhtml"
    logging.info("Editar bem-sucedido")


def editarPerfilNome(driver,nome):
    driver.get("http://localhost:8080/InvestiDor/faces/indexPerfil.xhtml")


    investir_button = driver.find_element(By.ID, "perfil:editarperfil")
    investir_button.click()
    import time
    time.sleep(5)


    nome_cdb = driver.find_element(By.ID, "input_formAlt:editNome")
    nome_cdb.clear()

    nome_cdb.send_keys(nome)
   

    investir_button = driver.find_element(By.ID, "formAlt:editarsalvar")
    investir_button.click()
    driver.implicitly_wait(10)
    assert driver.current_url == "http://localhost:8080/InvestiDor/faces/indexPerfil.xhtml"
    logging.info("Editar bem-sucedido")

def editarPerfilSobreNome(driver,nome):
    driver.get("http://localhost:8080/InvestiDor/faces/indexPerfil.xhtml")


    investir_button = driver.find_element(By.ID, "perfil:editarperfil")
    investir_button.click()
    import time
    time.sleep(5)


    nome_cdb = driver.find_element(By.ID, "input_formAlt:editSobrenome")
    nome_cdb.clear()

    nome_cdb.send_keys(nome)
   

    investir_button = driver.find_element(By.ID, "formAlt:editarsalvar")
    investir_button.click()
    driver.implicitly_wait(10)
    assert driver.current_url == "http://localhost:8080/InvestiDor/faces/indexPerfil.xhtml"
    logging.info("Editar bem-sucedido")

    