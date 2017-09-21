package in.cfcomputing.yggdrasil.adapters.email.processors;


import in.cfcomputing.commons.yggdrasil.domain.email.EmailRequest;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.thymeleaf.context.Context;

public abstract class AbstractEmailTemplateProcessor extends AbstractEmailPreprocessor {
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    @Override
    protected void buildTemplateContext(final Exchange exchange, final Context context) {
        final EmailRequest emailRequest = exchange.getIn().getBody(EmailRequest.class);
        context.setVariable("receiverName", emailRequest.getReceiverName());
        context.setVariable("emailId", emailRequest.getEmailId());

        buildEmailContextParams(exchange, context);

        final String subject = messageSource.getMessage(emailRequest.getSubject(), new Object[]{}, context.getLocale());
        exchange.getIn().setHeader("subject", subject);
        exchange.getIn().setHeader("to", emailRequest.getEmailId());
    }

    protected abstract void buildEmailContextParams(final Exchange exchange, final Context context);
}
