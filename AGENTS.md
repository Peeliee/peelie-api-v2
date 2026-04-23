# Core Principles
- **Readability** always comes first. Code should be **written for humans**, not machines.
- **Lombok Usage**: Use `@Getter`, `@RequiredArgsConstructor`. Avoid Field Injection.

# Project Coding Standards (Custom DDD)
## Layered Architecture Rules
- **Interfaces**: Controller, DTO, Mapper. (External communication only)
- **Application**: XxxxFacade. (Orchestration, No business logic, No @Transactional, **No direct Repository access**)
- **Domain**: Entity, Service, Interface (Reader, Store, Executor). (Core logic, @Transactional here)
- **Infrastructure**: Low-level implementations (ReaderImpl, StoreImpl, JPA, Redis).

## Subdomain Boundary Rules
- A subdomain is defined as the immediate child directory under `peelie/`.
- Entities must not reference entities from other subdomains directly.
- Cross-subdomain interaction must occur only through Facade or Application layer.
- Within the same subdomain, entity object references are allowed.
- Domain Services must not directly invoke Services from other subdomains; they may only publish domain events for cross-domain coordination.

## Cross-Subdomain Exception Rule
- A Service implementation within a subdomain may reference the `Reader` interface of another subdomain.
- Only `Reader` interfaces are allowed for cross-subdomain access in the Domain layer.
- Direct access to Entities, Services, Stores, or Executors of other subdomains is strictly prohibited in the Domain layer.

- Application Facades may invoke another subdomain's Service only for non-critical, best-effort side effects
  (e.g., notifications), where failures must not affect core business success.
- Such best-effort calls must be isolated and failure-tolerant (catch/log, no rollback propagation to the core flow).

## Naming & Responsibility
1. **Domain Service**: Only one entry point per domain (e.g., `OrderService`).
2. **Domain Support**: Use specific names: `Reader`, `Store`, `Executor`, `Factory`.
3. **No Cross-Service Reference in Domain**: Domain Services must not refer to other subdomain Services. Use Domain Events or Facade-level orchestration.
4. **DIP (Dependency Inversion)**: Domain layer defines interfaces; Infrastructure implements them.
5. **Reader Method Naming**:
  - `get~`: Use when the value must exist. If not found, throw an exception. (`Optional` return is not allowed)
  - `find~`: Use when the value may be absent. Return `Optional<T>`.

## Dependency Direction & Flexibility
- The standard dependency flow: 
  - Interfaces depends on Application, 
  - Application depends on Domain
  - Infrastructure depends on Domain.
- These rules are **strongly recommended but not absolute**.
- If following this architecture feels significantly inefficient or inappropriate for a specific task, you must **stop** and **ask** me for an exception before proceeding with the implementation.

## Review guidelines

- must leave Review in KOREAN.
---
applyTo: 'src/test/**'
---
# Testing Standards & Conventions
- **Framework**: JUnit 5, **AssertJ**, **BDDMockito**.
- **Structure**: Always use `// given`, `// when`, `// then` comments.
- **Display**: Use `@DisplayName` with "성공: ..." or "실패: ..." format.