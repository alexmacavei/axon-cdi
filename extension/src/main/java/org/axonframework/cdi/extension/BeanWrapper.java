package org.axonframework.cdi.extension;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.enterprise.util.AnnotationLiteral;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class BeanWrapper<T> implements Bean<T>, PassivationCapable {

  private final Class<T> clazz;
  private final Supplier<T> supplier;

  public BeanWrapper(final Class<T> clazz, final Supplier<T> supplier) {
    this.clazz = clazz;
    this.supplier = supplier;
  }

  @Override
  public T create(final CreationalContext<T> ctx) {
    return this.supplier.get();
  }

  @Override
  public void destroy(final T instance, final CreationalContext<T> ctx) {
    ctx.release();
  }

  @Override
  public String getName() {
    return clazz.getSimpleName();
  }

  @SuppressWarnings("serial")
  @Override
  public Set<Annotation> getQualifiers() {
    final Set<Annotation> qualifiers = new HashSet<>();
    qualifiers.add(new AnnotationLiteral<Default>() { });
    qualifiers.add(new AnnotationLiteral<Any>() { });
    return qualifiers;
  }

  @Override
  public Class<? extends Annotation> getScope() {
    return ApplicationScoped.class;
  }

  @Override
  public Set<Class<? extends Annotation>> getStereotypes() {
    return Collections.emptySet();
  }

  @Override
  public Set<Type> getTypes() {
    final Set<Type> types = new HashSet<>();
    types.add(clazz);
    types.add(Object.class);
    return types;
  }

  @Override
  public Class<?> getBeanClass() {
    return clazz;
  }

  @Override
  public Set<InjectionPoint> getInjectionPoints() {
    return Collections.emptySet();
  }

  @Override
  public boolean isAlternative() {
    return false;
  }

  @Override
  public boolean isNullable() {
    return false;
  }

  @Override
  public String getId() {
    return clazz.toString() + "#" + supplier.toString();
  }
}
