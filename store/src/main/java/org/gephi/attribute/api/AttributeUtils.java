/*
 * Copyright 2012-2013 Gephi Consortium
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gephi.attribute.api;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.gephi.attribute.time.*;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 *
 * @author mbastian
 */
public class AttributeUtils {

    private static final Set<Class> SUPPORTED_TYPES;
    private static final Map<Class, Class> TYPES_STANDARDIZATION;
    private static final DateTimeFormatter DATE_TIME_FORMATTER;

    static {
        SUPPORTED_TYPES = new HashSet<Class>();

        //Primitives
        SUPPORTED_TYPES.add(Boolean.class);
        SUPPORTED_TYPES.add(boolean.class);
        SUPPORTED_TYPES.add(Integer.class);
        SUPPORTED_TYPES.add(int.class);
        SUPPORTED_TYPES.add(Short.class);
        SUPPORTED_TYPES.add(short.class);
        SUPPORTED_TYPES.add(Long.class);
        SUPPORTED_TYPES.add(long.class);
        SUPPORTED_TYPES.add(Byte.class);
        SUPPORTED_TYPES.add(byte.class);
        SUPPORTED_TYPES.add(Float.class);
        SUPPORTED_TYPES.add(float.class);
        SUPPORTED_TYPES.add(Double.class);
        SUPPORTED_TYPES.add(double.class);
        SUPPORTED_TYPES.add(Character.class);
        SUPPORTED_TYPES.add(char.class);

        //Objects
        SUPPORTED_TYPES.add(String.class);

        //Prinitives Array
        SUPPORTED_TYPES.add(Boolean[].class);
        SUPPORTED_TYPES.add(boolean[].class);
        SUPPORTED_TYPES.add(Integer[].class);
        SUPPORTED_TYPES.add(int[].class);
        SUPPORTED_TYPES.add(Short[].class);
        SUPPORTED_TYPES.add(short[].class);
        SUPPORTED_TYPES.add(Long[].class);
        SUPPORTED_TYPES.add(long[].class);
        SUPPORTED_TYPES.add(Byte[].class);
        SUPPORTED_TYPES.add(byte[].class);
        SUPPORTED_TYPES.add(Float[].class);
        SUPPORTED_TYPES.add(float[].class);
        SUPPORTED_TYPES.add(Double[].class);
        SUPPORTED_TYPES.add(double[].class);
        SUPPORTED_TYPES.add(Character[].class);
        SUPPORTED_TYPES.add(char[].class);

        //Objects array
        SUPPORTED_TYPES.add(String[].class);

        //Dynamic
        SUPPORTED_TYPES.add(TimestampBooleanSet.class);
        SUPPORTED_TYPES.add(TimestampIntegerSet.class);
        SUPPORTED_TYPES.add(TimestampShortSet.class);
        SUPPORTED_TYPES.add(TimestampLongSet.class);
        SUPPORTED_TYPES.add(TimestampByteSet.class);
        SUPPORTED_TYPES.add(TimestampFloatSet.class);
        SUPPORTED_TYPES.add(TimestampDoubleSet.class);
        SUPPORTED_TYPES.add(TimestampCharSet.class);
        SUPPORTED_TYPES.add(TimestampStringSet.class);

        //Primitive types standardization
        TYPES_STANDARDIZATION = new HashMap<Class, Class>();
        TYPES_STANDARDIZATION.put(boolean.class, Boolean.class);
        TYPES_STANDARDIZATION.put(int.class, Integer.class);
        TYPES_STANDARDIZATION.put(short.class, Short.class);
        TYPES_STANDARDIZATION.put(long.class, Long.class);
        TYPES_STANDARDIZATION.put(byte.class, Byte.class);
        TYPES_STANDARDIZATION.put(float.class, Float.class);
        TYPES_STANDARDIZATION.put(double.class, Double.class);
        TYPES_STANDARDIZATION.put(char.class, Character.class);

        //Array standardization
        TYPES_STANDARDIZATION.put(Boolean[].class, boolean[].class);
        TYPES_STANDARDIZATION.put(Integer[].class, int[].class);
        TYPES_STANDARDIZATION.put(Short[].class, short[].class);
        TYPES_STANDARDIZATION.put(Long[].class, long[].class);
        TYPES_STANDARDIZATION.put(Byte[].class, byte[].class);
        TYPES_STANDARDIZATION.put(Float[].class, float[].class);
        TYPES_STANDARDIZATION.put(Double[].class, double[].class);
        TYPES_STANDARDIZATION.put(Character[].class, char[].class);

        //Datetime
        DATE_TIME_FORMATTER = ISODateTimeFormat.dateOptionalTimeParser();
    }

    public static Object parse(String str, Class typeClass) {
        if (typeClass.equals(String.class)) {
            return str;
        } else if (typeClass.equals(Byte.class)) {
            return new Byte(removeDecimalDigitsFromString(str));
        } else if (typeClass.equals(Short.class)) {
            return new Short(removeDecimalDigitsFromString(str));
        } else if (typeClass.equals(Integer.class)) {
            return new Integer(removeDecimalDigitsFromString(str));
        } else if (typeClass.equals(Long.class)) {
            return new Long(removeDecimalDigitsFromString(str));
        } else if (typeClass.equals(Float.class)) {
            return new Float(str);
        } else if (typeClass.equals(Double.class)) {
            return new Double(str);
        } else if (typeClass.equals(Boolean.class)) {
            return Boolean.valueOf(str);
        } else if (typeClass.equals(Character.class)) {
            return new Character(str.charAt(0));
        } else if (typeClass.equals(BigInteger.class)) {
            return new BigInteger(removeDecimalDigitsFromString(str));
        } else if (typeClass.equals(BigDecimal.class)) {
            return new BigDecimal(str);
        }
        return null;
    }

    public static Class getPrimitiveType(Class type) {
        if (!type.isPrimitive()) {
            if (type.equals(Boolean.class)) {
                return boolean.class;
            } else if (type.equals(Integer.class)) {
                return int.class;
            } else if (type.equals(Short.class)) {
                return short.class;
            } else if (type.equals(Long.class)) {
                return long.class;
            } else if (type.equals(Byte.class)) {
                return byte.class;
            } else if (type.equals(Float.class)) {
                return float.class;
            } else if (type.equals(Double.class)) {
                return double.class;
            } else if (type.equals(Character.class)) {
                return char.class;
            }
        }
        throw new IllegalArgumentException("The type should be a wrapped primitive");
    }

    public static Object getPrimitiveArray(Object[] array) {
        Class arrayClass = array.getClass().getComponentType();
        if (!arrayClass.isPrimitive()) {
            Class primitiveClass = getPrimitiveType(arrayClass);

            int arrayLength = array.length;
            Object primitiveArray = Array.newInstance(primitiveClass, arrayLength);

            for (int i = 0; i < arrayLength; i++) {
                Object obj = array[i];
                if (obj != null) {
                    Array.set(array, i, obj);
                }
            }
            return primitiveArray;
        }
        return array;
    }

    public static Set<Class> getSupportedTypes() {
        return SUPPORTED_TYPES;
    }

    public static boolean isSupported(Class type) {
        return SUPPORTED_TYPES.contains(type);
    }

    public static Class getStandardizedType(Class type) {
        Class t = TYPES_STANDARDIZATION.get(type);
        if (t != null) {
            return t;
        }
        return type;
    }

    public boolean isStandardizedType(Class type) {
        return TYPES_STANDARDIZATION.get(type).equals(type);
    }

    public static Class<? extends TimestampValueSet> getDynamicType(Class type) {
        type = getStandardizedType(type);
        if (type.equals(Boolean.class)) {
            return TimestampBooleanSet.class;
        } else if (type.equals(Integer.class)) {
            return TimestampIntegerSet.class;
        } else if (type.equals(Short.class)) {
            return TimestampShortSet.class;
        } else if (type.equals(Long.class)) {
            return TimestampLongSet.class;
        } else if (type.equals(Byte.class)) {
            return TimestampByteSet.class;
        } else if (type.equals(Float.class)) {
            return TimestampFloatSet.class;
        } else if (type.equals(Double.class)) {
            return TimestampDoubleSet.class;
        } else if (type.equals(Character.class)) {
            return TimestampCharSet.class;
        } else if (type.equals(String.class)) {
            return TimestampStringSet.class;
        }
        throw new IllegalArgumentException("Unsupported type");
    }

    public static Object standardizeValue(Object value) {
        Class type = value.getClass();
        if (type.isArray()) {
            return getPrimitiveArray((Object[]) value);
        }
        return value;
    }

    public static double parseDateTime(String dateTime) {
        return DATE_TIME_FORMATTER.parseDateTime(dateTime).getMillis();
    }

    /**
     * Removes the decimal digits and point of the numbers of string when
     * necessary. Used for trying to parse decimal numbers as not decimal. For
     * example BigDecimal to BigInteger.
     *
     * @param s String to remove decimal digits
     * @return String without dot and decimal digits.
     */
    private static String removeDecimalDigitsFromString(String s) {
        return removeDecimalDigitsFromStringPattern.matcher(s).replaceAll("");
    }
    private static final Pattern removeDecimalDigitsFromStringPattern = Pattern.compile("\\.[0-9]*");
}
