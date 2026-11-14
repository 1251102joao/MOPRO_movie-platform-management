# Movie Platform Management

A Java console application that manages a movie and TV platform in the style of IMDb: films, series (with seasons and episodes), actors, genres, users, ratings and personal watch lists.

Built with **object-oriented design** (inheritance, interfaces, polymorphism), a menu-driven text interface and **data persistence** through Java serialization. Academic project developed at ISEP.

## Features

**Visitor (no login)**
- Browse films, series and actors, search by title or name
- Sort films by title or rating, and actors by name or number of films
- View film and series details

**Viewer**
- Mark films and episodes as watched
- Rate and comment on films and episodes
- Keep a personal list of films and episodes to watch

**Administrator**
- Manage films, series (seasons and episodes), actors and genres
- Link actors and genres to films, series and episodes
- Manage users and see which viewers have watched the most films
- Platform overview, plus save and load of all data to a file

## Project structure

```
src/main/java/org/example
├── model/   domain classes (Filme, Serie, Temporada, Episodio, Ator, Genero, Espectador, Admin, IMDB, ...)
├── ui/      console menus (Main, MenuInicial, MenuAdministrador, MenuUtilizadorRegistado, ...)
└── utils/   input and date helpers
src/test/java/org/example/PlataformaTest.java   JUnit tests
```

`IMDB` is the central class that holds users, actors, films and series. Shared behaviour is expressed through the interfaces `Pesquisavel` (searchable) and `MarcavelComoVisto` (can be marked as watched), and the abstract classes `Recurso` and `UtilizadorRegistado`.

## Run

Requires a JDK and Maven. The Java version is set in `pom.xml`.

```bash
git clone https://github.com/1251102joao/movie-platform-management.git
cd movie-platform-management
mvn test                                  # run the unit tests
mvn compile exec:java                     # start the application
```

The user interface is in European Portuguese.

## Author

**João Pimentel**, Systems Engineering student at ISEP (Porto).
