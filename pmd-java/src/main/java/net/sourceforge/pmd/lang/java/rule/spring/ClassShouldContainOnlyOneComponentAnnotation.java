package net.sourceforge.pmd.lang.java.rule.spring;

import net.sourceforge.pmd.lang.java.ast.ASTMarkerAnnotation;
import net.sourceforge.pmd.lang.java.ast.ASTTypeDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ClassShouldContainOnlyOneComponentAnnotation extends AbstractJavaRule
{

    @Override
    public Object visit ( ASTTypeDeclaration typeDeclaration, Object data )
    {
        List<String> annotationIdentifiersToCheck = new ArrayList<>();
        annotationIdentifiersToCheck.add( "RestController" );
        annotationIdentifiersToCheck.add( "Controller" );
        annotationIdentifiersToCheck.add( "Service" );
        annotationIdentifiersToCheck.add( "Repository" );
        annotationIdentifiersToCheck.add( "Component" );

        List<String> annotationsAsString = typeDeclaration.findDescendantsOfType( ASTMarkerAnnotation.class )
                .stream().map( a -> a.jjtGetLastToken().toString() ).collect( Collectors.toList() );

        int found = 0;
        for ( String toCheck : annotationIdentifiersToCheck )
        {
            if ( annotationsAsString.contains( toCheck ) )
            {
                found++;
            }
        }
        if ( found > 1 )
        {
            super.addViolation( data, typeDeclaration );
        }
        return super.visit( typeDeclaration, data );
    }
}
