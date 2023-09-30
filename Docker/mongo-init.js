db = db.getSiblingDB('rinchabackend2023-q3');
db.createCollection('pessoas');
db.pessoas.createIndex(
    {"apelido": 1},
    {unique: true});

db.pessoas.createIndex({
        "busca": "text"
    }
)