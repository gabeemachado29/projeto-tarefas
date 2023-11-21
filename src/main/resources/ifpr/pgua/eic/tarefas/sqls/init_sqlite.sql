CREATE TABLE IF NOT EXISTS categoria (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome varchar(255) NOT NULL,
    descricao varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tarefa (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    titulo varchar(255) NOT NULL,
    descricao varchar(255) NOT NULL,
    dtprazo DATE NOT NULL,
    categoria INTEGER NOT NULL,
    FOREIGN KEY (categoria) REFERENCES categoria(id),
);
