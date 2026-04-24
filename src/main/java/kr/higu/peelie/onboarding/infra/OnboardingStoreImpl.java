package kr.higu.peelie.onboarding.infra;

import kr.higu.peelie.onboarding.domain.Onboarding;
import kr.higu.peelie.onboarding.domain.OnboardingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnboardingStoreImpl implements OnboardingStore {

    private final OnboardingRepository onboardingRepository;

    @Override
    public Onboarding store(Onboarding onboarding) {
        return onboardingRepository.save(onboarding);
    }
}
