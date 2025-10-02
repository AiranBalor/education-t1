package ru.t1.education;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.Before;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.Assert.*;

public class PeopleNumberGroupingTest {
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    private Path testFile;
    
    @Before
    public void setUp() throws IOException {
        testFile = tempFolder.newFile("people.txt").toPath();
    }
    
    @Test
    public void testGroupPeopleByNumberWithValidData() throws IOException {
        Files.write(testFile, Arrays.asList(
            "Vasya:5",
            "Petya:3", 
            "Anya:5",
            "Masha:3",
            "Kolya:2"
        ));

        Map<Integer, List<String>> result = PeopleNumberGrouping.processPeople(testFile);

        assertNotNull(result);
        assertEquals(3, result.size());
        
        assertTrue(result.get(5).containsAll(Arrays.asList("Vasya", "Anya")));
        assertEquals(2, result.get(5).size());
        
        assertTrue(result.get(3).containsAll(Arrays.asList("Petya", "Masha")));
        assertEquals(2, result.get(3).size());
        
        assertEquals(Collections.singletonList("Kolya"), result.get(2));
    }
    
    @Test
    public void testGroupPeopleByNumberWithEmptyLinesAndSpaces() throws IOException {
        Files.write(testFile, Arrays.asList(
            "Vasya:5  ",
            "",
            "Petya:3",
            "  Anya:5  ",
            "  ",
            "Sergey:1"
        ));

        Map<Integer, List<String>> result = PeopleNumberGrouping.processPeople(testFile);

        assertNotNull(result);
        assertEquals(3, result.size());
        
        assertTrue(result.get(5).containsAll(Arrays.asList("Vasya", "Anya")));
        assertEquals(2, result.get(5).size());
        
        assertEquals(Collections.singletonList("Petya"), result.get(3));
        assertEquals(Collections.singletonList("Sergey"), result.get(1));
    }
    
    @Test
    public void testGroupPeopleByNumberWithPeopleWithoutNumbers() throws IOException {
        Files.write(testFile, Arrays.asList(
            "Vasya:5",
            "Petya:",
            "Anya:5", 
            "Masha",
            "Kolya:2",
            "Olga:4"
        ));

        Map<Integer, List<String>> result = PeopleNumberGrouping.processPeople(testFile);

        assertNotNull(result);
        assertEquals(3, result.size());
        
        assertTrue(result.get(5).containsAll(Arrays.asList("Vasya", "Anya")));
        assertEquals(2, result.get(5).size());
        
        assertEquals(Collections.singletonList("Kolya"), result.get(2));
        assertEquals(Collections.singletonList("Olga"), result.get(4));
    }
    
    @Test
    public void testGroupPeopleByNumberWithDifferentNameCases() throws IOException {
        Files.write(testFile, Arrays.asList(
            "VASYA:5",
            "petya:3", 
            "AnYa:5",
            "mAKSIM:2",
            "ANNA-MARIA:1"
        ));

        Map<Integer, List<String>> result = PeopleNumberGrouping.processPeople(testFile);

        assertNotNull(result);
        assertEquals(4, result.size());
        
        assertTrue(result.get(5).containsAll(Arrays.asList("Vasya", "Anya")));
        assertEquals(2, result.get(5).size());
        
        assertEquals(Collections.singletonList("Petya"), result.get(3));
        assertEquals(Collections.singletonList("Maksim"), result.get(2));
        assertEquals(Collections.singletonList("Anna-Maria"), result.get(1));
    }
    
    @Test
    public void testGroupPeopleByNumberWithEmptyFile() throws IOException {
        Files.write(testFile, Collections.emptyList());

        Map<Integer, List<String>> result = PeopleNumberGrouping.processPeople(testFile);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testGroupPeopleByNumberWithOnlyInvalidEntries() throws IOException {
        Files.write(testFile, Arrays.asList(
            "Petya:",
            "Masha",
            "",
            "  "
        ));

        Map<Integer, List<String>> result = PeopleNumberGrouping.processPeople(testFile);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testGroupPeopleByNumberWithDuplicateNumbers() throws IOException {
        Files.write(testFile, Arrays.asList(
            "Ivan:1",
            "Petr:1",
            "Maria:1",
            "Anna:2",
            "Oleg:1"
        ));

        Map<Integer, List<String>> result = PeopleNumberGrouping.processPeople(testFile);

        assertNotNull(result);
        assertEquals(2, result.size());
        
        assertTrue(result.get(1).containsAll(Arrays.asList("Ivan", "Petr", "Maria", "Oleg")));
        assertEquals(4, result.get(1).size());
        
        assertEquals(Collections.singletonList("Anna"), result.get(2));
    }
    
    @Test(expected = IOException.class)
    public void testGroupPeopleByNumberFileNotFound() throws IOException {
        Path nonExistentFile = Path.of("non_existent_people_file.txt");
        PeopleNumberGrouping.processPeople(nonExistentFile);
    }
    
    @Test
    public void testNameCapitalization() {
        assertEquals("Vasya", PeopleNumberGrouping.capitalizeName("vasya"));
        assertEquals("Vasya", PeopleNumberGrouping.capitalizeName("Vasya"));
        assertEquals("Petya", PeopleNumberGrouping.capitalizeName("pETYA"));
        assertEquals("Anna-Maria", PeopleNumberGrouping.capitalizeName("anna-maria"));
        assertEquals("Anna-Maria", PeopleNumberGrouping.capitalizeName("ANNA-MARIA"));
        assertEquals("Jean-Pierre", PeopleNumberGrouping.capitalizeName("jean-pierre"));
        assertEquals("Mary-Jane", PeopleNumberGrouping.capitalizeName("MARY-JANE"));
        assertEquals("John", PeopleNumberGrouping.capitalizeName("JOHN"));
        assertEquals("", PeopleNumberGrouping.capitalizeName(""));
        assertEquals("A", PeopleNumberGrouping.capitalizeName("a"));
        assertEquals("A", PeopleNumberGrouping.capitalizeName("A"));
        assertEquals("Li", PeopleNumberGrouping.capitalizeName("li"));
    }
    
    @Test
    public void testGroupPeopleByNumberWithLargeNumbers() throws IOException {
        Files.write(testFile, Arrays.asList(
            "Alexey:100",
            "Dmitry:999",
            "Ekaterina:100",
            "Fedor:0",
            "Galina:-1"
        ));

        Map<Integer, List<String>> result = PeopleNumberGrouping.processPeople(testFile);

        assertNotNull(result);
        assertEquals(4, result.size());
        
        assertTrue(result.get(100).containsAll(Arrays.asList("Alexey", "Ekaterina")));
        assertEquals(2, result.get(100).size());
        
        assertEquals(Collections.singletonList("Dmitry"), result.get(999));
        assertEquals(Collections.singletonList("Fedor"), result.get(0));
        assertEquals(Collections.singletonList("Galina"), result.get(-1));
    }
    
    @Test
    public void testGroupPeopleByNumberWithSpecialCharacters() throws IOException {
        Files.write(testFile, Arrays.asList(
            "John:1",
            "Mary-Jane:2",
            "Jean-Pierre:3",
            "Li Wei:4"
        ));

        Map<Integer, List<String>> result = PeopleNumberGrouping.processPeople(testFile);

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(Collections.singletonList("John"), result.get(1));
        assertEquals(Collections.singletonList("Mary-Jane"), result.get(2));
        assertEquals(Collections.singletonList("Jean-Pierre"), result.get(3));
        assertEquals(Collections.singletonList("Li wei"), result.get(4));
    }
}