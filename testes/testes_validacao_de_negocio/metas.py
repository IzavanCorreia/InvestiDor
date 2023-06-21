
from keys import pegarloginesenha,criarInvestimento,criarInvestimentoDataInicialMaiorQueAFinal,criarInvestimentoEditar

from testes import login,lougout,irPerfil,verificarTeste,criarMeta,pegarIdsCardsMetas,diferencaSimetrica,editarMeta,registrar
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
num_tests_passed,num_tests_failed = verificarTeste("Indo para renda fixa", num_tests_passed,num_tests_failed , irPerfil(driver))


logging.info("Criar Meta")
logging.info("Pegar os ids dos cards")
ids_elementos = pegarIdsCardsMetas(driver)
print(ids_elementos)
criarMeta(driver,"joão",7)

ids_elementos_depois = pegarIdsCardsMetas(driver)
if ids_elementos != ids_elementos_depois:
        logging.info("Renda fixa criada bem-sucedido")
        num_tests_passed = num_tests_passed + 1

        for id_deletar in diferencaSimetrica(ids_elementos,ids_elementos_depois):
                
                logging.info("Editar elemento criado")

                num_tests_passed,num_tests_failed = verificarTeste("Editando Renda Fixa", num_tests_passed,num_tests_failed , editarMeta(driver,"Lucas","5000",id_deletar))
                




                



                logging.info("Deletar elemento criado")
                driver.get("http://localhost:8080/InvestiDor/faces/indexPerfil.xhtml")

                investir_button = driver.find_element(By.ID, "formCards:deletar" + str(id_deletar))
                investir_button.click()
                driver.implicitly_wait(10)
                import time
                time.sleep(10)

                investir_button = driver.find_element(By.ID, "formDeletar:certeza" + str(id_deletar))
                investir_button.click()
                driver.implicitly_wait(10)
                
               
        verificar_se_deletou = pegarIdsCardsMetas(driver)
        if ids_elementos == verificar_se_deletou:
                logging.info(f"Id {id_deletar} Deletado!")
                num_tests_passed = num_tests_passed + 1
        else:
                logging.info(f"Id {id_deletar} não foi Deletado!")
                num_tests_failed = num_tests_failed + 1
else:
    logging.info("Meta falhou!")
    num_tests_failed = num_tests_failed + 1


logging.info("Iniciando teste de lougout...")
num_tests_passed,num_tests_failed = verificarTeste("Lougout", num_tests_passed,num_tests_failed ,lougout(driver))



logging.info("\033[1;32mNúmero de testes bem-sucedidos: " + str(num_tests_passed) + "\033[1;0m")
if num_tests_failed > 0:
        logging.error("\033[1;31mNúmero de testes mal-sucedidos: " + str(num_tests_failed) + "\033[1;0m")
else:
        logging.info("\033[1;32mTodos os testes foram bem-sucedidos!\033[1;0m")
