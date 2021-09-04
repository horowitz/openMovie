# openMovie
[![Maintainability](https://api.codeclimate.com/v1/badges/6db12a24d3fa5839ffda/maintainability)](https://codeclimate.com/github/horowitz/openMovie/maintainability)
![CI](https://github.com/horowitz/openMovie/actions/workflows/codecov.yml/badge.svg)
[![codecov](https://codecov.io/gh/horowitz/openMovie/branch/main/graph/badge.svg?token=AXMKDL22OR)](https://codecov.io/gh/horowitz/openMovie)

Show case Android practices using [the Movie db](https://www.themoviedb.org/)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="assets/open_movie.gif" width="40%" height="40%"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="assets/open_movie_2.gif" width="40%" height="40%"/>

## Tech stack
- 100% testable Kotlin code
- Clean Multimodular Architecture
- Presentation layer built based on Unidirectional data flow (UDF) and single entry points
- Latest AGP 7+
- Dagger Hilt
- CI with Github actions
- High test coverage using Unit, Instrumentation and Integration tests

## Architecture

#### Project
This project aims for showcasing a multi modular architecture based on 2 concepts: Libraries and Features

- **Features** contains all user facing functionality (e.g. Discover, Movie Details)
- **Libraries** are shared modules that help the building of features (e.g. network,  ui, test)
