package com.github.schmittjoaopedro.mcc.engine;

import com.github.schmittjoaopedro.mcc.object.SourceClass;
import com.github.schmittjoaopedro.mcc.utils.MccUtils;
import org.junit.Assert;
import org.junit.Test;

public class TestMccUtils {

    @Test
    public void testMccUtils() {

        MccUtils mcc = new MccUtils();
        System.out.println(mcc);

        String RULE_TEMPLATE =
                "package teste;\n"
                        + "\n"
                        + "import comp.test.service.object.ComponentBase;\n"
                        + "\n"
                        + "public class GenericRule {\n"
                        + "\n"
                        + "		private static final GenericRule INSTANCE = new GenericRule();\n"
                        + "\n"
                        + "		public static GenericRule getInstance() {\n"
                        + "			return GenericRule.INSTANCE;\n"
                        + "		}\n"
                        + "\n"
                        + "		public void execute(ComponentBase _this) {\n"
                        + "			//Make your code here...\n"
                        + "		}\n"
                        + "\n"
                        + "}\n";

        Assert.assertEquals("GenericRule", MccUtils.extractClassNameFromSourceCode(RULE_TEMPLATE));
        Assert.assertEquals("teste", MccUtils.extractPackageFromSourceCode(RULE_TEMPLATE));
        SourceClass sourceClass = MccUtils.extractSourceClassFromSourceCode(RULE_TEMPLATE);
        Assert.assertEquals("teste", sourceClass.getPackageName());
        Assert.assertEquals("GenericRule", sourceClass.getClassName());
        Assert.assertEquals(RULE_TEMPLATE, sourceClass.getSourceCode());

    }

    @Test
    public void testMccUtilsClass() {

        String RULE_TEMPLATE =
                "package teste;\n"
                        + "\n"
                        + "import comp.test.service.object.ComponentBase;\n"
                        + "\n"
                        + "private class GenericRule {\n"
                        + "\n"
                        + "		private static final GenericRule INSTANCE = new GenericRule();\n"
                        + "\n"
                        + "		public static GenericRule getInstance() {\n"
                        + "			return GenericRule.INSTANCE;\n"
                        + "		}\n"
                        + "\n"
                        + "		public void execute(ComponentBase _this) {\n"
                        + "			//Make your code here...\n"
                        + "		}\n"
                        + "\n"
                        + "}\n";

        Assert.assertEquals("GenericRule", MccUtils.extractClassNameFromSourceCode(RULE_TEMPLATE));
        Assert.assertEquals("teste", MccUtils.extractPackageFromSourceCode(RULE_TEMPLATE));
        SourceClass sourceClass = MccUtils.extractSourceClassFromSourceCode(RULE_TEMPLATE);
        Assert.assertEquals("teste", sourceClass.getPackageName());
        Assert.assertEquals("GenericRule", sourceClass.getClassName());
        Assert.assertEquals(RULE_TEMPLATE, sourceClass.getSourceCode());

    }

    @Test
    public void testMccUtilsClassImplements() {

        String RULE_TEMPLATE =
                "package teste;\n"
                        + "\n"
                        + "import comp.test.service.object.ComponentBase;\n"
                        + "\n"
                        + "private class GenericRule implements Serializable {\n"
                        + "\n"
                        + "		private static final GenericRule INSTANCE = new GenericRule();\n"
                        + "\n"
                        + "		public static GenericRule getInstance() {\n"
                        + "			return GenericRule.INSTANCE;\n"
                        + "		}\n"
                        + "\n"
                        + "		public void execute(ComponentBase _this) {\n"
                        + "			//Make your code here...\n"
                        + "		}\n"
                        + "\n"
                        + "}\n";

        Assert.assertEquals("GenericRule", MccUtils.extractClassNameFromSourceCode(RULE_TEMPLATE));
        Assert.assertEquals("teste", MccUtils.extractPackageFromSourceCode(RULE_TEMPLATE));
        SourceClass sourceClass = MccUtils.extractSourceClassFromSourceCode(RULE_TEMPLATE);
        Assert.assertEquals("teste", sourceClass.getPackageName());
        Assert.assertEquals("GenericRule", sourceClass.getClassName());
        Assert.assertEquals(RULE_TEMPLATE, sourceClass.getSourceCode());

    }

    @Test
    public void testMccUtilsClassExtends() {

        String RULE_TEMPLATE =
                "package teste;\n"
                        + "\n"
                        + "import comp.test.service.object.ComponentBase;\n"
                        + "\n"
                        + "private class GenericRule extends String {\n"
                        + "\n"
                        + "		private static final GenericRule INSTANCE = new GenericRule();\n"
                        + "\n"
                        + "		public static GenericRule getInstance() {\n"
                        + "			return GenericRule.INSTANCE;\n"
                        + "		}\n"
                        + "\n"
                        + "		public void execute(ComponentBase _this) {\n"
                        + "			//Make your code here...\n"
                        + "		}\n"
                        + "\n"
                        + "}\n";

        Assert.assertEquals("GenericRule", MccUtils.extractClassNameFromSourceCode(RULE_TEMPLATE));
        Assert.assertEquals("teste", MccUtils.extractPackageFromSourceCode(RULE_TEMPLATE));
        SourceClass sourceClass = MccUtils.extractSourceClassFromSourceCode(RULE_TEMPLATE);
        Assert.assertEquals("teste", sourceClass.getPackageName());
        Assert.assertEquals("GenericRule", sourceClass.getClassName());
        Assert.assertEquals(RULE_TEMPLATE, sourceClass.getSourceCode());

    }


    @Test
    public void testMccUtilsClassError() {

        String RULE_TEMPLATE =
                "package teste;\n"
                        + "\n"
                        + "import comp.test.service.object.ComponentBase;\n"
                        + "\n"
                        + "class GenericRule extends String {\n"
                        + "\n"
                        + "		private static final GenericRule INSTANCE = new GenericRule();\n"
                        + "\n"
                        + "		public static GenericRule getInstance() {\n"
                        + "			return GenericRule.INSTANCE;\n"
                        + "		}\n"
                        + "\n"
                        + "		public void execute(ComponentBase _this) {\n"
                        + "			//Make your code here...\n"
                        + "		}\n"
                        + "\n"
                        + "}\n";

        Assert.assertEquals(null, MccUtils.extractClassNameFromSourceCode(RULE_TEMPLATE));
        Assert.assertEquals("teste", MccUtils.extractPackageFromSourceCode(RULE_TEMPLATE));

    }

}
