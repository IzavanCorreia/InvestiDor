from selenium.webdriver.common.by import By

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
    logout_button = driver.find_element(By.ID, "formMenuUsuario:menu")
    logout_button.click()

    logout_button = driver.find_element(By.ID, "formMenuUsuario:rendafixa")
    logout_button.click()

    driver.implicitly_wait(10)
    assert driver.current_url == "http://localhost:8080/InvestiDor/faces/indexRendaFixa.xhtml"
    return 1
    