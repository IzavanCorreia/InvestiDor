
import os
from models import Investimento

def pegarloginesenha():
    # Definindo as variáveis de ambiente
    os.environ['senha_email'] = 'asfFWQHFUWQ@13'
    os.environ['email'] = 'admin@hotmail.com'
    
    cpf = "843.268.290-01"
    nome = "admin"
    sobrenome= "admin" 
    telefone = "81 987878787"
    # Exemplo de uso das variáveis de ambiente

    return str(os.environ['email']),str(os.environ['senha_email']),cpf,nome,sobrenome,telefone

def criarInvestimento():
    nome = "CDB BANCO SEGURO 107% CDI"
    indexador = "CDI"
    quantidade = "10"
    valor_unit = "1000"
    valor_atual = "1000"
    data_inicial = "11/05/2023"
    data_final = "11/05/2026"
    tipo_investimento = "cdb"
    imposto = "sim"
    return Investimento(nome,indexador,quantidade,valor_unit,valor_atual,data_inicial,data_final,tipo_investimento,imposto)


def criarInvestimentoDataInicialMaiorQueAFinal():
    nome = "CDB BANCO SEGURO 107% CDI"
    indexador = "CDI"
    quantidade = "10"
    valor_unit = "1000"
    valor_atual = "1000"
    data_inicial = "11/05/2026"
    data_final =  "11/05/2023"
    tipo_investimento = "cdb"
    imposto = "sim"
    return Investimento(nome,indexador,quantidade,valor_unit,valor_atual,data_inicial,data_final,tipo_investimento,imposto)

def criarInvestimentoEditar():
    nome = "CDB BANCO SEGURO 110% CDI"
    indexador = "CDI"
    quantidade = "1"
    valor_unit = "1000"
    valor_atual = "1001"
    data_inicial = "12/05/2026"
    data_final =  "12/05/2023"
    tipo_investimento = "cdb"
    imposto = "sim"
    return Investimento(nome,indexador,quantidade,valor_unit,valor_atual,data_inicial,data_final,tipo_investimento,imposto)


def criarInvestimentoEditarPreco():
    nome = "CDB BANCO SEGURO 110% CDI"
    indexador = "CDI"
    quantidade = "1"
    valor_unit = "1000"
    valor_atual = "1001"
    data_inicial = "12/05/2026"
    data_final =  "12/05/2023"
    tipo_investimento = "cdb"
    imposto = "sim"
    return Investimento(nome,indexador,quantidade,valor_unit,valor_atual,data_inicial,data_final,tipo_investimento,imposto)