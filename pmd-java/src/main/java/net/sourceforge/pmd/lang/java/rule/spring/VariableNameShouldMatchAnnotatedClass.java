package net.sourceforge.pmd.lang.java.rule.spring;

import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.Token;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class VariableNameShouldMatchAnnotatedClass extends AbstractJavaRule
{

    @Override
    public Object visit ( ASTFieldDeclaration fieldDeclaration, Object data )
    {
        List<String> annotationIdentifiersToCheck = new ArrayList<>();
        annotationIdentifiersToCheck.add( "Controller" );
        annotationIdentifiersToCheck.add( "Service" );
        annotationIdentifiersToCheck.add( "Repository" );

        String classIdentifier = ( ( Token )fieldDeclaration.jjtGetFirstToken() ).image;
        String variableIdentifier = ( ( Token )fieldDeclaration.jjtGetFirstToken() ).next.image;

        annotationIdentifiersToCheck.forEach( aId ->
        {
            if ( StringUtils.containsIgnoreCase( classIdentifier, aId ) )
            {
                if ( !StringUtils.containsIgnoreCase( variableIdentifier, aId ) )
                {
                    super.addViolation( data, fieldDeclaration );
                }
            }
        } );

        return super.visit( fieldDeclaration, data );
    }

}
