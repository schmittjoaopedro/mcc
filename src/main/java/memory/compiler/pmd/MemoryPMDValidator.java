package memory.compiler.pmd;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import memory.compiler.message.MemoryCompilerException;
import memory.compiler.message.MessageStatus;
import memory.compiler.object.SourceClass;
import memory.compiler.object.SourceTask;
import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RulePriority;
import net.sourceforge.pmd.RuleSetFactory;
import net.sourceforge.pmd.RuleViolation;
import net.sourceforge.pmd.renderers.Renderer;
import net.sourceforge.pmd.util.datasource.DataSource;

/**
 * MemoryPMDValidator run PMD validation to java source class.
 * If the validation fail a Exception is thrown.
 *
 * @author schmittjoaopedro
 * @version 1.0.0
 * @since 1.0.0
 */
public class MemoryPMDValidator {

    private static final String PMD_VALIDATION_FAILED = "PMD Validation failed\n";

    private static final String BRK_LINE = "\n";

    private static final String DOTDOT = " : ";

    /**
     * PMD global configurations
     */
    private PMDConfiguration configuration;

    /**
     * PMD level
     */
    private RuleSetFactory ruleSetFactory;

    /**
     * PMD base configuration
     */
    public MemoryPMDValidator() {
        this.configuration = new PMDConfiguration();
        this.configuration.setRuleSets("rulesets/java/unusedcode.xml,rulesets/java/basic.xml,rulesets/java/codesize.xml,rulesets/java/strings.xml");
        this.configuration.setSourceEncoding("UTF-8");
        this.configuration.setThreads(0);
        this.ruleSetFactory = new RuleSetFactory();
        this.ruleSetFactory.setMinimumPriority(RulePriority.MEDIUM);
    }

    /**
     * Execute the PMD validation, if some class validation fail
     * then a MemoryCompilerException is thrown with the violations
     *
     * @param sourceTask
     * @throws MemoryCompilerException
     */
    public void check(SourceTask sourceTask) {
        for(SourceClass sourceClass : sourceTask.getSourcesClass()) {
            this.check(sourceClass);
        }
    }

    /**
     * Execute PMD validation for SourceClass, if the class fail,
     * then a MemoryCompilerException is thrown with the violations
     *
     * @param sourceClass
     * @throws MemoryCompilerException
     */
    public void check(SourceClass sourceClass) {

        List<DataSource> dataSources = Arrays.asList(new StringDataSource(sourceClass.getSourceCode()));
        RuleContext ruleContext = new RuleContext();
        StringBuilder violationReport = new StringBuilder();
        PMD.processFiles(configuration, ruleSetFactory, dataSources, ruleContext, Collections.<Renderer> emptyList());
        if(!ruleContext.getReport().isEmpty()) {
            violationReport.append(PMD_VALIDATION_FAILED);
            Iterator<RuleViolation> violations = ruleContext.getReport().iterator();
            while (violations.hasNext()) {
                RuleViolation ruleViolation = violations.next();
                if(!ruleViolation.getClassName().trim().equals("")) {
                    violationReport.append(ruleViolation.getClassName());
                    violationReport.append(DOTDOT);
                    violationReport.append(ruleViolation.getBeginLine());
                    violationReport.append(DOTDOT);
                }
                violationReport.append(ruleViolation.getDescription());
                violationReport.append(BRK_LINE);
            }
            throw new MemoryCompilerException(violationReport.toString(), MessageStatus.PMD_ERROR);
        }

    }

}
