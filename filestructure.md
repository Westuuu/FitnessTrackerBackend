pl/edu/pwr/gymproject
│
├── GymProjectApplication.java            // Główna klasa startowa Spring Boot
│
├── config                                // Konfiguracja globalna
│   ├── OpenApiConfig.java                // Konfiguracja Swagger UI
│   ├── SecurityConfig.java               // Security Chain (JWT + DB Filter) [cite: 67]
│   └── WebConfig.java                    // CORS (dla React Native) [cite: 72]
│
├── core                                  // Infrastruktura techniczna (niezależna od biznesu)
│   ├── database                          // IMPLEMENTACJA ROUTINGU DB [cite: 361-364]
│   │   ├── DataSourceConfig.java         // Definicja 3 beanów DataSource (Admin, Trainer, Trainee)
│   │   ├── RoutingDataSource.java        // Logika przełączania (extends AbstractRoutingDataSource)
│   │   ├── DatabaseContextHolder.java    // ThreadLocal przechowujący bieżącą rolę
│   │   ├── DatabaseRoutingFilter.java    // Filtr ustawiający kontekst na podstawie JWT
│   │   └── DbRole.java                   // Enum: ADMIN, TRAINER, TRAINEE
│   │
│   ├── security                          // Obsługa tokenów i autentykacji
│   │   ├── JwtService.java               // Generowanie/weryfikacja tokenów
│   │   ├── JwtAuthenticationFilter.java  // Filtr autoryzacyjny
│   │   ├── CustomUserDetailsService.java // Pobieranie usera (tabela login_credential) [cite: 1087]
│   │   └── UserPrincipal.java            // Wrapper na użytkownika Spring Security
│   │
│   └── exception                         // Globalna obsługa błędów
│       ├── GlobalExceptionHandler.java   // @ControllerAdvice
│       └── ResourceNotFoundException.java
│
└── domain                                // Moduły biznesowe (zgodnie z rozdziałem 4.1 PDF)
│
├── auth                              // Logowanie i Rejestracja
│   ├── AuthController.java
│   ├── AuthService.java
│   └── dto
│       ├── LoginRequest.java
│       └── RegisterRequest.java
│
├── gym                               // Zarządzanie siłownią [cite: 851]
│   ├── GymController.java
│   ├── GymService.java
│   ├── GymRepository.java
│   ├── GymEntity.java                // Tabela: gym, address, opening_hours
│   └── dto
│       └── GymDetailsDto.java
│
├── user                              // Profile użytkowników [cite: 1043]
│   ├── UserController.java
│   ├── UserService.java
│   ├── UserRepository.java
│   ├── model
│   │   ├── UserEntity.java           // Tabela: user
│   │   ├── TrainerInfoEntity.java    // Tabela: trainer_info
│   │   ├── TraineeInfoEntity.java    // Tabela: trainee_info
│   │   └── BodyMetricsEntity.java    // Tabela: body_metrics [cite: 1304]
│   └── dto
│       └── UserProfileDto.java
│
├── trainingplan                      // Plany treningowe [cite: 949]
│   ├── TrainingPlanController.java
│   ├── TrainingPlanService.java      // Logika tworzenia/edycji planów
│   ├── TrainingPlanRepository.java
│   ├── model
│   │   ├── TrainingPlanEntity.java   // Tabela: training_plan
│   │   ├── WorkoutTemplateDay.java   // Tabela: workout_template_day
│   │   └── ExerciseTemplate.java     // Tabela: exercise_template [cite: 824]
│   └── dto
│       └── CreatePlanRequest.java    // Zgodnie z mockupem Rys. 6 [cite: 438]
│
├── session                           // Wykonywanie treningów (Sesje) [cite: 794]
│   ├── SessionController.java
│   ├── SessionService.java           // Logika logowania wyników
│   ├── SessionRepository.java
│   ├── model
│   │   ├── WorkoutSessionEntity.java // Tabela: workout_session
│   │   ├── ExerciseInstance.java     // Tabela: exercise_instance
│   │   └── ExerciseSet.java          // Tabela: exercise_instance_set
│   └── dto
│       └── LogWorkoutDto.java        // Zgodnie z mockupem Rys. 11 [cite: 622]
│
├── goal                              // Cele treningowe [cite: 869]
│   ├── GoalController.java
│   ├── GoalService.java
│   ├── GoalRepository.java
│   ├── GoalEntity.java               // Tabela: goal
│   └── dto
│       └── GoalDto.java              // Zgodnie z mockupem Rys. 10 [cite: 570]
│
└── membership                        // Obsługa karnetów [cite: 1149]
├── MembershipController.java
├── MembershipService.java
├── MembershipRepository.java     // Sprawdzanie ważności karnetu
└── MembershipEntity.java         // Tabela: membership