package io.swiftwallet.yggdrasil.core.support;

import org.springframework.context.MessageSource;
import org.thymeleaf.Arguments;
import org.thymeleaf.messageresolver.AbstractMessageResolver;
import org.thymeleaf.messageresolver.MessageResolution;
import org.thymeleaf.spring4.messageresolver.SpringMessageResolver;

public class YggdrasilMessageResolver extends AbstractMessageResolver {
    private final SpringMessageResolver messageResolver;

    public YggdrasilMessageResolver(final MessageSource messageSource) {
        messageResolver = new SpringMessageResolver();
        messageResolver.setMessageSource(messageSource);
    }

    @Override
    public MessageResolution resolveMessage(Arguments arguments, String s, Object[] objects) {
        return messageResolver.resolveMessage(arguments, s, objects);
    }
}