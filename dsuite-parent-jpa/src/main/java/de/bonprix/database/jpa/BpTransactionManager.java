package de.bonprix.database.jpa;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import de.bonprix.database.ettool.EtToolLoggingRepository;

public class BpTransactionManager extends JpaTransactionManager {
    private static final long serialVersionUID = 1L;

    @Value("${database.ettool.logging.active}")
    boolean useEttoolLogging;

    @Resource
    private EtToolLoggingRepository etToolLoggingRepository;

    public BpTransactionManager() {
        super();

        // overriding logger name to the superclass because we are not doing anything special here
        this.logger = LogFactory.getLog(JpaTransactionManager.class);
    }

    public BpTransactionManager(final EntityManagerFactory emf) {
        super(emf);

        // overriding logger name to the superclass because we are not doing anything special here
        this.logger = LogFactory.getLog(JpaTransactionManager.class);
    }

    @Override
    protected void doBegin(final Object transaction, final TransactionDefinition definition) {
        super.doBegin(transaction, definition);

        if (this.useEttoolLogging) {
            this.etToolLoggingRepository.init();
        }
    }

}
