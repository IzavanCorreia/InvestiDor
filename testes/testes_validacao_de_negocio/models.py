class Investimento:
    def __init__(self,nome,indexador,quantidade,valor_unit,valor_atual,data_inicial,data_final,tipo_investimento,imposto):
        self.nome = nome
        self.indexador = indexador
        self.quantidade = quantidade
        self.valor_unit = valor_unit
        self.valor_atual = valor_atual
        self.data_inicial = data_inicial
        self.data_final = data_final
        self.tipo_investimento = tipo_investimento
        self.imposto = imposto
