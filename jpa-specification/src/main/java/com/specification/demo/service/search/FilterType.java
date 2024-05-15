package com.specification.demo.service.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specification.demo.exception.CustomServiceException;
import com.specification.demo.payload.request.ConditionRequest;
import com.specification.demo.payload.response.ErrorType;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public enum FilterType {
  EQUAL {
    public <T> Predicate build(
        Root<T> root,
        CriteriaBuilder cb,
        ConditionRequest condition,
        BaseSearchExpression searchExpression,
        RoleType searchRoleType) {

      Object value =
          condition.getFieldType().parse(validateValueAndReturn(condition.getValue()).toString());

      Expression<?> key = searchExpression.getExpression(root, condition.getKey(), searchRoleType);

      value = convertToEnumIfEnum(value, key);
      return cb.equal(key, value);
    }
  },

  NOT_EQUAL {
    public <T> Predicate build(
        Root<T> root,
        CriteriaBuilder cb,
        ConditionRequest condition,
        BaseSearchExpression searchExpression,
        RoleType searchRoleType) {

      Object value =
          condition.getFieldType().parse(validateValueAndReturn(condition.getValue()).toString());
      Expression<?> key = searchExpression.getExpression(root, condition.getKey(), searchRoleType);
      value = convertToEnumIfEnum(value, key);
      return cb.notEqual(key, value);
    }
  },

  CONTAINS {
    public <T> Predicate build(
        Root<T> root,
        CriteriaBuilder cb,
        ConditionRequest condition,
        BaseSearchExpression searchExpression,
        RoleType searchRoleType) {

      if (FieldType.STRING.equals(condition.getFieldType())) {
        String value = validateValueAndReturn(condition.getValue()).toString().toUpperCase();
        Expression<String> key =
            (Expression<String>)
                searchExpression.getExpression(root, condition.getKey(), searchRoleType);
        return cb.like(cb.upper(key), "%" + value + "%");
      }
      return null;
    }
  },

  ENDS_WITH {
    public <T> Predicate build(
        Root<T> root,
        CriteriaBuilder cb,
        ConditionRequest condition,
        BaseSearchExpression searchExpression,
        RoleType searchRoleType) {

      if (FieldType.STRING.equals(condition.getFieldType())) {
        String value = validateValueAndReturn(condition.getValue()).toString().toUpperCase();
        Expression<String> key =
            (Expression<String>)
                searchExpression.getExpression(root, condition.getKey(), searchRoleType);
        return cb.like(cb.upper(key), "%" + value);
      }
      return null;
    }
  },

  STARTS_WITH {
    public <T> Predicate build(
        Root<T> root,
        CriteriaBuilder cb,
        ConditionRequest condition,
        BaseSearchExpression searchExpression,
        RoleType searchRoleType) {

      if (FieldType.STRING.equals(condition.getFieldType())) {
        String value = validateValueAndReturn(condition.getValue()).toString().toUpperCase();
        Expression<String> key =
            (Expression<String>)
                searchExpression.getExpression(root, condition.getKey(), searchRoleType);
        return cb.like(cb.upper(key), value + "%");
      }
      return null;
    }
  },

  IN {
    public <T> Predicate build(
        Root<T> root,
        CriteriaBuilder cb,
        ConditionRequest condition,
        BaseSearchExpression searchExpression,
        RoleType searchRoleType) {

      validateValueAndReturn(condition.getValues());
      List<Object> values = condition.getValues();
      Expression<String> key =
          (Expression<String>)
              searchExpression.getExpression(root, condition.getKey(), searchRoleType);
      values = convertToEnumIfEnum(values, key);
      CriteriaBuilder.In<Object> inClause = cb.in(key);
      for (Object value : values) {
        inClause.value(value);
      }
      return inClause;
    }
  },

  GRATER_THAN {
    public <T> Predicate build(
        Root<T> root,
        CriteriaBuilder cb,
        ConditionRequest condition,
        BaseSearchExpression searchExpression,
        RoleType searchRoleType) {

      Object value =
          condition.getFieldType().parse(validateValueAndReturn(condition.getValue()).toString());

      if (FieldType.STRING.equals(condition.getFieldType())) {
        Expression<String> key =
            (Expression<String>)
                searchExpression.getExpression(root, condition.getKey(), searchRoleType);
        return cb.greaterThan(cb.upper(key), value.toString());
      } else if (FieldType.DATE.equals(condition.getFieldType())) {
        if (value instanceof LocalDateTime) {
          LocalDateTime startDate = (LocalDateTime) value;
          Expression<LocalDateTime> key =
              (Expression<LocalDateTime>)
                  searchExpression.getExpression(root, condition.getKey(), searchRoleType);

          return cb.and(cb.greaterThan(key, startDate));
        } else if (value instanceof LocalDate) {
          LocalDate startDate = (LocalDate) value;
          Expression<LocalDate> key =
              (Expression<LocalDate>)
                  searchExpression.getExpression(root, condition.getKey(), searchRoleType);

          return cb.and(cb.greaterThan(key, startDate));
        }
      } else if (FieldType.NUMBER.equals(condition.getFieldType())) {
        Number numberValue = (Number) value;
        Expression<Number> key =
            (Expression<Number>)
                searchExpression.getExpression(root, condition.getKey(), searchRoleType);
        return cb.gt(key, numberValue);
      }
      return null;
    }
  },

  GRATER_THAN_OR_EQUAL {
    public <T> Predicate build(
        Root<T> root,
        CriteriaBuilder cb,
        ConditionRequest condition,
        BaseSearchExpression searchExpression,
        RoleType searchRoleType) {

      Object value =
          condition.getFieldType().parse(validateValueAndReturn(condition.getValue()).toString());

      if (FieldType.STRING.equals(condition.getFieldType())) {
        Expression<String> key =
            (Expression<String>)
                searchExpression.getExpression(root, condition.getKey(), searchRoleType);
        return cb.greaterThanOrEqualTo(cb.upper(key), value.toString());
      } else if (FieldType.DATE.equals(condition.getFieldType())) {
        if (value instanceof LocalDateTime) {
          LocalDateTime startDate = (LocalDateTime) value;
          Expression<LocalDateTime> key =
              (Expression<LocalDateTime>)
                  searchExpression.getExpression(root, condition.getKey(), searchRoleType);
          return cb.and(cb.greaterThanOrEqualTo(key, startDate));
        } else if (value instanceof LocalDate) {
          LocalDate startDate = (LocalDate) value;
          Expression<LocalDate> key =
              (Expression<LocalDate>)
                  searchExpression.getExpression(root, condition.getKey(), searchRoleType);
          return cb.and(cb.greaterThanOrEqualTo(key, startDate));
        }

      } else if (FieldType.NUMBER.equals(condition.getFieldType())) {
        Number numberValue = (Number) value;
        Expression<Number> key =
            (Expression<Number>)
                searchExpression.getExpression(root, condition.getKey(), searchRoleType);
        return cb.ge(key, numberValue);
      }
      return null;
    }
  },

  LESS_THAN {
    public <T> Predicate build(
        Root<T> root,
        CriteriaBuilder cb,
        ConditionRequest condition,
        BaseSearchExpression searchExpression,
        RoleType searchRoleType) {

      Object value =
          condition.getFieldType().parse(validateValueAndReturn(condition.getValue()).toString());

      if (FieldType.STRING.equals(condition.getFieldType())) {
        Expression<String> key =
            (Expression<String>)
                searchExpression.getExpression(root, condition.getKey(), searchRoleType);
        return cb.lessThan(cb.upper(key), value.toString());
      } else if (FieldType.DATE.equals(condition.getFieldType())) {
        if (value instanceof LocalDateTime) {
          LocalDateTime startDate = (LocalDateTime) value;
          Expression<LocalDateTime> key =
              (Expression<LocalDateTime>)
                  searchExpression.getExpression(root, condition.getKey(), searchRoleType);
          return cb.and(cb.lessThan(key, startDate));
        } else if (value instanceof LocalDate) {
          LocalDate startDate = (LocalDate) value;
          Expression<LocalDate> key =
              (Expression<LocalDate>)
                  searchExpression.getExpression(root, condition.getKey(), searchRoleType);
          return cb.and(cb.lessThan(key, startDate));
        }
      } else if (FieldType.NUMBER.equals(condition.getFieldType())) {
        Number numberValue = (Number) value;
        Expression<Number> key =
            (Expression<Number>)
                searchExpression.getExpression(root, condition.getKey(), searchRoleType);
        return cb.lt(key, numberValue);
      }
      return null;
    }
  },

  LESS_THAN_OR_EQUAL {
    public <T> Predicate build(
        Root<T> root,
        CriteriaBuilder cb,
        ConditionRequest condition,
        BaseSearchExpression searchExpression,
        RoleType searchRoleType) {

      Object value =
          condition.getFieldType().parse(validateValueAndReturn(condition.getValue()).toString());

      if (FieldType.STRING.equals(condition.getFieldType())) {
        Expression<String> key =
            (Expression<String>)
                searchExpression.getExpression(root, condition.getKey(), searchRoleType);
        return cb.lessThanOrEqualTo(cb.upper(key), value.toString());
      } else if (FieldType.DATE.equals(condition.getFieldType())) {
        if (value instanceof LocalDateTime) {
          LocalDateTime startDate = (LocalDateTime) value;
          Expression<LocalDateTime> key =
              (Expression<LocalDateTime>)
                  searchExpression.getExpression(root, condition.getKey(), searchRoleType);
          return cb.and(cb.lessThanOrEqualTo(key, startDate));
        } else if (value instanceof LocalDate) {
          LocalDate startDate = (LocalDate) value;
          Expression<LocalDate> key =
              (Expression<LocalDate>)
                  searchExpression.getExpression(root, condition.getKey(), searchRoleType);
          return cb.and(cb.lessThanOrEqualTo(key, startDate));
        }
      } else if (FieldType.NUMBER.equals(condition.getFieldType())) {
        Number numberValue = (Number) value;
        Expression<Number> key =
            (Expression<Number>)
                searchExpression.getExpression(root, condition.getKey(), searchRoleType);
        return cb.le(key, numberValue);
      }
      return null;
    }
  },

  BETWEEN {
    public <T> Predicate build(
        Root<T> root,
        CriteriaBuilder cb,
        ConditionRequest condition,
        BaseSearchExpression searchExpression,
        RoleType searchRoleType) {

      Object value =
          condition.getFieldType().parse(validateValueAndReturn(condition.getValue()).toString());
      Object valueTo =
          condition.getFieldType().parse(validateValueAndReturn(condition.getValueTo()).toString());

      if (FieldType.DATE.equals(condition.getFieldType())) {
        if (value instanceof LocalDateTime) {
          LocalDateTime startDate = (LocalDateTime) value;
          LocalDateTime endDate = (LocalDateTime) valueTo;
          Expression<LocalDateTime> key =
              (Expression<LocalDateTime>)
                  searchExpression.getExpression(root, condition.getKey(), searchRoleType);
          return cb.and(
              cb.greaterThanOrEqualTo(key, startDate), cb.lessThanOrEqualTo(key, endDate));
        } else if (value instanceof LocalDate) {
          LocalDate startDate = (LocalDate) value;
          LocalDate endDate = (LocalDate) valueTo;
          Expression<LocalDate> key =
              (Expression<LocalDate>)
                  searchExpression.getExpression(root, condition.getKey(), searchRoleType);
          return cb.and(
              cb.greaterThanOrEqualTo(key, startDate), cb.lessThanOrEqualTo(key, endDate));
        }

      } else if (FieldType.NUMBER.equals(condition.getFieldType())) {
        Number start = (Number) value;
        Number end = (Number) valueTo;
        Expression<Number> key =
            (Expression<Number>)
                searchExpression.getExpression(root, condition.getKey(), searchRoleType);
        return cb.and(cb.ge(key, start), cb.le(key, end));
      }
      return null;
    }
  },

  IS_NULL {
    public <T> Predicate build(
        Root<T> root,
        CriteriaBuilder cb,
        ConditionRequest condition,
        BaseSearchExpression searchExpression,
        RoleType searchRoleType) {

      Expression<?> key = searchExpression.getExpression(root, condition.getKey(), searchRoleType);
      return cb.isNull(key);
    }
  };

  public Object validateValueAndReturn(Object obj) {
    if (obj == null) {
      throw new CustomServiceException(ErrorType.FILTER_PARAMETER_CANNOT_NULL_ERROR);
    }
    return obj;
  }

  public Object convertToEnumIfEnum(Object value, Expression<?> expression) {
    if (expression.getJavaType().isEnum()) {

      for (Object obj : expression.getJavaType().getEnumConstants()) {

        for (Field field : expression.getJavaType().getFields()) {
          if (obj.toString().equals(field.getName())) {
            if (field.getAnnotations() != null
                && field.getAnnotations().length > 0
                && field.getAnnotations()[0] instanceof JsonProperty) {
              JsonProperty jsonProperty = (JsonProperty) field.getAnnotations()[0];
              if (value.equals(jsonProperty.value())) {
                return obj;
              }
            } else if (value.equals(obj.toString())) {
              return obj;
            }
          }
        }
      }
    }

    return value;
  }

  public List<Object> convertToEnumIfEnum(List<Object> values, Expression<?> expression) {
    List<Object> list = new ArrayList<>();

    for (Object value : values) {
      list.add(convertToEnumIfEnum(value, expression));
    }

    return list;
  }

  public abstract <T> Predicate build(
      Root<T> root,
      CriteriaBuilder cb,
      ConditionRequest condition,
      BaseSearchExpression searchExpression,
      RoleType searchRoleType);
}
