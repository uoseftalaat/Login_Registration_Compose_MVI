# Login Registration Compose MVI

This project is a **Login and Registration system** built using **Jetpack Compose** and the **Model-View-Intent (MVI)** architectural pattern. It was developed as part of a task during my internship at **Areeb Technology**, with the aim of exploring clean architecture principles and modern Android development practices.

The app demonstrates how to structure a Compose-based project around **unidirectional data flow**, ensuring better separation of concerns, easier state handling, and improved testability.

To encapsulate the business logic cleanly, the project uses **UseCase classes** — making the logic modular, reusable, and testable. Dependency management across the layers is handled with **Koin**, keeping the codebase lightweight and easy to manage without boilerplate.

All Composable UI elements are written to be **stateless and reusable**, allowing flexibility in usage and making the components easier to preview and test in isolation.

On the testing front, **JUnit5** and **Mockito** were used to write unit tests for both the ViewModel and UseCases, ensuring that key logic paths are covered and reliable.

---

## 🧩 Project Highlights

- ✅ Modern UI built with **Jetpack Compose**
- 📐 **MVI pattern** for predictable and maintainable state handling
- 🧠 **UseCases** for encapsulating business logic
- ♻️ **Stateless Composables** for reusability and previewing
- 💉 **Koin** for dependency injection
- 🧪 Unit testing with **JUnit5** and **Mockito**

---

## 🧪 Testing

Unit tests are written for:
- Input validation logic
- ViewModel state updates and intent handling
- Business logic encapsulated in UseCases

Testing frameworks:
- `JUnit5`
- `Mockito`

---

## 📘 Learning Outcome

Through this task, I gained experience with:

- Applying the **MVI architecture** in a real-world Compose project
- Writing clean, **reusable UI** using **stateless composables**
- Structuring **UseCases** to handle business rules
- Integrating **Koin** for dependency injection
- Writing **unit tests** with JUnit5 and Mockito

---

## project-root

├── presentation   # UI, ViewModels, and state handling

├── domain         # UseCases and business logic

├── di             # Koin dependency injection modules

├── data           # (Optional) Repositories and data sources

└── test           # Unit tests using JUnit5 and Mockito




## 🚀 Getting Started

1. **Clone the repository**

```bash
git clone https://github.com/uoseftalaat/Login_Registration_Compose_MVI.git


