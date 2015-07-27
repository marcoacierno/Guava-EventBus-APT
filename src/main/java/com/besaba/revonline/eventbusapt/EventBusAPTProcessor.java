package com.besaba.revonline.eventbusapt;

import com.google.common.eventbus.Subscribe;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Set;


@SupportedAnnotationTypes("com.google.common.eventbus.Subscribe")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class EventBusAPTProcessor extends AbstractProcessor {
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (final Element element : roundEnv.getElementsAnnotatedWith(Subscribe.class)) {
      if (element.getKind() != ElementKind.METHOD) {
        // can't happen
        continue;
      }

      final ExecutableElement method = ((ExecutableElement) element);
      final List<? extends VariableElement> parameters = method.getParameters();

      final int countParameters = parameters.size();
      if (countParameters != 1) {
        processingEnv.getMessager().printMessage(
            Diagnostic.Kind.ERROR,
            String.format("Found %d parameter(s) instead of 1", countParameters),
            method
        );
        return true;
      }

      final VariableElement firstParameter = parameters.get(0);
      final TypeMirror declaredType = firstParameter.asType();

      if (declaredType.getKind() != TypeKind.DECLARED) {
        processingEnv.getMessager().printMessage(
            Diagnostic.Kind.ERROR,
            "The first argument should extend Object. Primitive types will be registered, but won't work",
            firstParameter
        );
      }
    }
    return true;
  }
}
