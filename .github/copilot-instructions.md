# Core Principles
- **Readability** always comes first. Code should be **written for humans**, not machines.

# Project Coding Standards (Custom DDD)
## Layered Architecture Rules
- **Interfaces**: Controller, DTO, Mapper. (External communication only)
- **Application**: XxxxFacade. (Orchestration, No business logic, No @Transactional)
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
- The standard dependency flow is: Interfaces depends on Application, Application depends on Domain, and Infrastructure depends on Domain.
- These rules are strongly recommended but not absolute.
- If following this architecture feels significantly inefficient or inappropriate for a specific task, you must stop and ask me for an exception before proceeding with the implementation.

## Implementation Details
- Application Layer (Facade) handles task coordination and non-critical logic (e.g., Notifications).
- Infrastructure implementation should be 'Persistence Ignorant'.
- Use `@Service` for Domain Services, `@Component` for Infrastructure implementations.

# GitHub Workflow & Communication
## Language Policy
- **PR Descriptions**: Always write PR titles and descriptions in **Korean**.
- **Code Reviews**: Provide feedback, suggestions, and review comments in **Korean**.
- **Code**: Variable names, method names, and technical documentation within the code (KDoc/JDoc) should remain in **English**.

## PR Summary Format
- Use a bulleted list to summarize changes based on the layered architecture (e.g., "Domain 레이어에 OrderReader 추가").
- Highlight if any of the 'Core Principles' or 'Layered Architecture Rules' were specifically addressed or modified.

## Mandatory Language Rule
- All natural-language outputs MUST be in Korean.
- This includes: code review comments, PR review summaries, PR content, suggestions, and inline feedback.
- Do not use English for explanations unless directly quoting code, logs, error messages, or API identifiers.
- If a response was generated in English, regenerate it in Korean.

## PR Review Rule
- Do not make any code changes automatically.
- Do not create commits, push changes, or modify files without explicit user approval.
- Only provide review comments, suggestions, and explanations.
- Never apply fixes directly, even if the issue is obvious.