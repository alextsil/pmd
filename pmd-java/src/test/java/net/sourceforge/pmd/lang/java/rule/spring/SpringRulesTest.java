package net.sourceforge.pmd.lang.java.rule.spring;

import net.sourceforge.pmd.testframework.SimpleAggregatorTst;


public class SpringRulesTest extends SimpleAggregatorTst
{

    private static final String RULESET = "java-spring";

    @Override
    public void setUp ()
    {
        addRule( RULESET, "ClassNameShouldMatchAnnotation" );
        addRule( RULESET, "VariableNameShouldMatchAnnotatedClass" );
        addRule( RULESET, "EntityShouldContainNoArgsConstructor" );
        addRule( RULESET, "ClassShouldContainOnlyOneComponentAnnotation" );
        addRule( RULESET, "FieldShouldNotHaveAutowired" );
        addRule( RULESET, "ComponentFieldsShouldBeFinal" );
        addRule( RULESET, "ResponseEntityMethodShouldBeAnnotated" );
        addRule( RULESET, "RequestMappingMethodShouldBePublic" );
    }

}
