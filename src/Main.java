import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static class SampleClass {
        private int field;

        public int getField() {
            return field;
        }

        public void setField(int field) {
            this.field = field;
        }

        @Override
        public String toString() {
            return "SampleClass [field=" + field + "]";
        }
    }

    public static void main(String[] args) {
//        // Create sample input values
//        String field = "field";
//        Class<SampleClass> clazz = SampleClass.class;
//        List<Integer> existingNumbers = new ArrayList<>();
//        existingNumbers.add(2);
//        existingNumbers.add(5);
//        existingNumbers.add(4);
//        existingNumbers.add(6);
//        existingNumbers.add(0);
//        List<SampleClass> objListWithNull = new ArrayList<>();
//        objListWithNull.add(new SampleClass());
//        objListWithNull.add(new SampleClass());
//        objListWithNull.add(new SampleClass());
//
//        long startTimeOptimized = System.nanoTime();
//
//        // Call the function
//        List<SampleClass> result = mapNextFieldValuesToObject(field, clazz, existingNumbers, objListWithNull);
//
//        // Output the result
//        System.out.println("Mapped Objects:");
//        for (Object obj : result) {
//            System.out.println(obj);
//        }
//
//        long endTimeOptimized = System.nanoTime();
//        long executionTimeOptimized = endTimeOptimized - startTimeOptimized;
//
//        System.out.println("Execution Time: " + executionTimeOptimized + " ns");
//        System.out.println("Result Size: " + result.size());

        String s = null;
        test(s);
        System.out.println(s);
    }

    private static void test(String abc) {
        abc = "asss";
    }

    public static <T> List<T> mapNextFieldValuesToObject(String field, Class<T> clazz,
                                                         List<Integer> existingNumbers, List<T> objListWithNull) {

        LinkedList<Object> objList = new LinkedList<>(objListWithNull);
        List<Object> returnList = new ArrayList<>();

        if (!existingNumbers.isEmpty())
            Collections.sort(existingNumbers);

        try {
            Field declaredField = clazz.getDeclaredField(field);
            declaredField.setAccessible(true);

            int listIdx = 0;
            int newPozycjaValue = 1;
            while (!objList.isEmpty()) {
                if (listIdx >= existingNumbers.size()) {
                    declaredField.set(objList.getFirst(), newPozycjaValue);
                    returnList.add(objList.removeFirst());
                    newPozycjaValue++;
                } else {
                    int curr = existingNumbers.get(listIdx);
                    int next = listIdx + 1 < existingNumbers.size() ? existingNumbers.get(listIdx + 1) : Integer.MAX_VALUE;
                    if ((newPozycjaValue + 1) == next
                            && newPozycjaValue == curr) {
                        newPozycjaValue++;
                        listIdx++;
                    } else if ((newPozycjaValue < next
                            && newPozycjaValue > curr)
                            || (newPozycjaValue < curr)) {
                        declaredField.set(objList.getFirst(), newPozycjaValue);
                        returnList.add(objList.removeFirst());
                        newPozycjaValue++;
                        if (newPozycjaValue == next)
                            listIdx++;
                    } else if (newPozycjaValue == curr
                            && newPozycjaValue < next) {
                        newPozycjaValue++;
                    }
                }
            }

        } catch (NoSuchFieldException
                 | SecurityException
                 | IllegalArgumentException
                 | IllegalAccessException e) {
            e.printStackTrace();
        }
        return (List<T>) returnList;
    }
}