# API REST — Portões do Céu (Sprint 2)

Base URL: `/api`. Os endpoints retornam JSON e erros no formato Problem Details.

## Pacientes
- `GET /pacientes` — lista pacientes.
- `GET /pacientes/{cpf}` — consulta por CPF.
- `POST /pacientes` — cadastra paciente.
- `PUT /pacientes/{cpf}` — atualiza dados sem permitir troca do CPF.
- `DELETE /pacientes/{cpf}` — remove paciente; bloqueado se houver internação ativa ou vínculo referencial.

Exemplo de corpo: `{"cpf":"12345678901","nome":"Maria Silva","dataNascimento":"1990-05-10","telefone":"31999999999","endereco":"Rua A, 10","email":"maria@example.com"}`.

## Profissionais
- `GET /profissionais?nome=ana` — lista ou filtra por nome.
- `GET /profissionais/especialidade/{especialidade}` — busca especialistas por área.
- `GET /profissionais/cirurgioes/area/{area}` — busca cirurgiões por área cirúrgica.
- `GET /profissionais/{registro}` — consulta por registro.
- `POST /profissionais` — cadastra `CLINICO`, `ESPECIALISTA` ou `CIRURGIAO`.
- `PUT /profissionais/{registro}` — atualiza sem trocar registro/categoria.
- `DELETE /profissionais/{registro}` — remove se não houver restrição referencial no banco.

Para especialista, informe `especialidade`; para cirurgião, informe `areaCirurgica` (enum `TipoCirurgia`).

## Consultas e emergências
- `GET /consultas` e `GET /consultas/{id}` — consulta registros.
- `POST /consultas` — cria modalidade `ROTINA`, `ESPECIALIZADA` ou `EMERGENCIA`.
- `PATCH /consultas/{id}/status` — recebe `{"acao":"CONFIRMAR"}`, `INICIAR`, `CONCLUIR`, `CANCELAR` ou `NAO_COMPARECEU` conforme modalidade e regras de transição.

Rotina exige profissional `CLINICO`; especializada exige `ESPECIALISTA` e especialidade compatível; emergência exige prioridade `BAIXA`, `MODERADA`, `ALTA` ou `CRITICA`.

## Quartos
- `GET /quartos?situacao=LIVRE&tipo=UTI` — lista e filtra.
- `GET /quartos/disponiveis?tipo=UTI` — lista quartos com leitos livres, inclusive parcialmente ocupados.
- `GET /quartos/{numero}` — consulta quarto.
- `POST /quartos` — cadastra quarto.
- `PUT /quartos/{numero}` — atualiza capacidade, tipo, situação e andar.
- `DELETE /quartos/{numero}` — remove apenas sem internações vinculadas.
- `PATCH /quartos/{numero}/concluir-limpeza`, `/iniciar-manutencao` e `/concluir-manutencao` — controla disponibilidade.

## Internações
- `POST /internacoes` — interna paciente em quarto disponível.
- `GET /internacoes` — lista internações ativas.
- `GET /internacoes/{id}` e `GET /internacoes/paciente/{cpf}` — consulta.
- `PATCH /internacoes/{id}/alta` — recebe `{"dataAlta":"2026-11-10T14:30:00"}`.
- `PATCH /internacoes/{id}/quarto` — recebe `{"novoNumeroQuarto":202}`.

A ocupação respeita a capacidade do quarto; ao sair o último paciente, o quarto passa para `EM_LIMPEZA`, e só volta a `LIVRE` após concluir a limpeza.

## Observações
- Datas são enviadas em ISO-8601 (`yyyy-MM-dd` ou `yyyy-MM-dd'T'HH:mm:ss`).
- Os enums devem ser enviados com os nomes exatos declarados no código.
- A entidade `ProcedimentoCirurgico` não está presente no código-base recebido; portanto, os endpoints desse módulo permanecem pendentes da implementação da Pessoa 4.
