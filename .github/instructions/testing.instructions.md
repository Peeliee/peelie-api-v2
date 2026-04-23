---
applyTo: 'src/test/**'
description: 'Core Testing Principles'
---

# Testing Standards & Conventions

Target: src/test/**

## Core Testing Principles
- **Readability**: Test code is documentation. Follow the "Readability first" principle.
- **Unit Testing**: Focus on testing a single unit (Class/Method) in isolation.
- **Consistency**: All tests must follow the same structure and naming convention.

## Technical Stack
- **Framework**: JUnit 5
- **Assertion Library**: **AssertJ** (`org.assertj.core.api.Assertions.assertThat`)
- **Mocking Library**: Mockito (Prefer `BDDMockito` for Given-When-Then flow)

## Required Test Structure (BDD Style)
Every test method must use the following three-phase structure with explicit comments:

1. **// given**: Set up the initial state, mocks, and input data.
2. **// when**: Execute the specific action or method being tested.
3. **// then**: Verify the outcome using AssertJ and verify mock interactions.

### Example Template:
```java
@Test
@DisplayName("성공: 주문이 정상적으로 생성된다")
void createOrder_Success() {
    // given
    var request = new OrderCommand.RegisterOrder(...);
    given(orderStore.store(any())).willReturn(order);

    // when
    var result = orderService.registerOrder(request);

    // then
    assertThat(result.getOrderToken()).isEqualTo("test_token");
    verify(orderStore, times(1)).store(any());
}