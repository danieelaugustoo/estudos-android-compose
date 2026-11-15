# Estudos de Android com Jetpack Compose

Apps que construí seguindo o curso [Android Basics with Compose](https://developer.android.com/courses/android-basics-compose/course), do Google, durante o curso técnico no SENAI em 2025.

Cada pasta era um repositório separado. Juntei tudo aqui com o histórico de commits original para deixar o perfil mais organizado.

**Sobre a autoria:** são codelabs. A base de vários apps é o código inicial ou a solução publicada pelo Google sob licença Apache 2.0, e o arquivo `LICENSE` original foi mantido em cada pasta. Minha parte foi seguir as etapas, completar as implementações e adaptar alguns apps (textos em português, telas e dados próprios).

## Apps

A ordem segue a do curso, do mais simples ao mais completo.

| Pasta | App | O que pratica | Quando |
|---|---|---|---|
| [`feliz-aniversario`](feliz-aniversario) | Feliz Aniversário | Primeiros composables, texto e imagem | mar/2025 |
| [`lemonade`](lemonade) | Lemonade | Estado com `remember` e interação por clique | mar/2025 |
| [`bons-habitos`](bons-habitos) | 30 Days of Wellness | Listas com `LazyColumn`, cards e tema Material 3 | abr/2025 |
| [`lunch-tray`](lunch-tray) | Lunch Tray | Navegação entre telas com Navigation Compose | abr/2025 |
| [`cupcake-app`](cupcake-app) | Cupcake | Fluxo de pedido em várias telas com ViewModel compartilhado | abr/2025 |
| [`city-app`](city-app) | Reply | Layout adaptável para celular e tablet | abr/2025 |
| [`dessert-clicker-viewmodel`](dessert-clicker-viewmodel) | Dessert Clicker | Mover o estado da tela para um ViewModel | abr/2025 |
| [`get-data-from-internet`](get-data-from-internet) | Affirmations | Lista rolável de cards com imagem | abr/2025 |
| [`bus-schedule-app`](bus-schedule-app) | Bus Schedule | Persistência local com Room | abr/2025 |
| [`voos`](voos) | Voos | Busca de voos com Room, DataStore e Hilt | abr/2025 |
| [`waterme`](waterme) | Water Me | Tarefas em segundo plano e notificações com WorkManager | abr/2025 |
| [`juice-tracker`](juice-tracker) | Juice Tracker | Compose combinado com Views e Room | abr/2025 |

## Como rodar

Abra a pasta do app (não a raiz deste repositório) no Android Studio, espere o Gradle sincronizar e rode em um emulador ou aparelho.
