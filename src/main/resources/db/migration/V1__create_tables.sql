CREATE TABLE produtos (
    id INTEGER PRIMARY KEY,
    nome TEXT NOT NULL,
    tipo_produto TEXT NOT NULL,
    rentabilidade_anual NUMERIC NOT NULL,
    risco TEXT NOT NULL,
    prazo_min_meses INTEGER NOT NULL,
    prazo_max_meses INTEGER NOT NULL,
    valor_min NUMERIC NOT NULL,
    valor_max NUMERIC NOT NULL
);

CREATE TABLE simulacoes (
    id INTEGER PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    produto_nome TEXT NOT NULL,
    tipo_produto TEXT NOT NULL,
    valor_investido NUMERIC NOT NULL,
    prazo_meses INTEGER NOT NULL,
    rentabilidade_aplicada NUMERIC NOT NULL,
    valor_final NUMERIC NOT NULL,
    data_simulacao TEXT NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_simulacoes_cliente_data
ON simulacoes (cliente_id, data_simulacao DESC);