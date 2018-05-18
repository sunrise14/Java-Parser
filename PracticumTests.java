
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PracticumTests {
	@Parameters(name="(#{index}: {0}) {1}")
	public static java.util.Collection<Object[]> data() {
		return java.util.Arrays.asList(new Object[][] {
			{ true,  "QUERY TRUE." },
			{ false, "QUERY FALSE." },
			{ false, "QUERY ~TRUE." },
			{ true,  "QUERY ~FALSE." },

			{ true,  "QUERY TRUE -> TRUE." },
			{ false, "QUERY TRUE -> FALSE." },
			{ true,  "QUERY FALSE -> TRUE." },
			{ true,  "QUERY FALSE -> FALSE." },

			{ true,  "QUERY TRUE <=> TRUE." },
			{ false, "QUERY TRUE <=> FALSE." },
			{ false, "QUERY FALSE <=> TRUE." },
			{ true,  "QUERY FALSE <=> FALSE." },

			{ true,  "LET p = TRUE;  LET q = TRUE;  QUERY p & q." },
			{ false, "LET p = TRUE;  LET q = FALSE; QUERY p & q." },
			{ false, "LET p = FALSE; LET q = TRUE;  QUERY p & q." },
			{ false, "LET p = FALSE; LET q = FALSE; QUERY p & q." },

			{ true,  "LET p = TRUE;  LET q = TRUE;  LET r = TRUE;   QUERY p & q & r." },
			{ false, "LET p = TRUE;  LET q = TRUE;  LET r = FALSE;  QUERY p & q & r." },
			{ false, "LET p = TRUE;  LET q = FALSE; LET r = TRUE;   QUERY p & q & r." },
			{ false, "LET p = TRUE;  LET q = FALSE; LET r = FALSE;  QUERY p & q & r." },
			{ false, "LET p = FALSE; LET q = TRUE;  LET r = TRUE;   QUERY p & q & r." },
			{ false, "LET p = FALSE; LET q = TRUE;  LET r = FALSE;  QUERY p & q & r." },
			{ false, "LET p = FALSE; LET q = FALSE; LET r = TRUE;   QUERY p & q & r." },
			{ false, "LET p = FALSE; LET q = FALSE; LET r = FALSE;  QUERY p & q & r." },

			{ true,  "LET p = TRUE;  LET q = TRUE;  QUERY p | q." },
			{ true,  "LET p = TRUE;  LET q = FALSE; QUERY p | q." },
			{ true,  "LET p = FALSE; LET q = TRUE;  QUERY p | q." },
			{ false, "LET p = FALSE; LET q = FALSE; QUERY p | q." },

			{ true,  "LET p = TRUE;  LET q = TRUE;  LET r = TRUE;   QUERY p | q | r." },
			{ true,  "LET p = TRUE;  LET q = TRUE;  LET r = FALSE;  QUERY p | q | r." },
			{ true,  "LET p = TRUE;  LET q = FALSE; LET r = TRUE;   QUERY p | q | r." },
			{ true,  "LET p = TRUE;  LET q = FALSE; LET r = FALSE;  QUERY p | q | r." },
			{ true,  "LET p = FALSE; LET q = TRUE;  LET r = TRUE;   QUERY p | q | r." },
			{ true,  "LET p = FALSE; LET q = TRUE;  LET r = FALSE;  QUERY p | q | r." },
			{ true,  "LET p = FALSE; LET q = FALSE; LET r = TRUE;   QUERY p | q | r." },
			{ false, "LET p = FALSE; LET q = FALSE; LET r = FALSE;  QUERY p | q | r." },

			{ true,  "LET p = TRUE;  LET q = TRUE;  QUERY p -> q." },
			{ false, "LET p = TRUE;  LET q = FALSE; QUERY p -> q." },
			{ true,  "LET p = FALSE; LET q = TRUE;  QUERY p -> q." },
			{ true,  "LET p = FALSE; LET q = FALSE; QUERY p -> q." },

			{ true,  "LET p = TRUE;  LET q = TRUE;  LET r = TRUE;   QUERY p -> q -> r." },
			{ false, "LET p = TRUE;  LET q = TRUE;  LET r = FALSE;  QUERY p -> q -> r." },
			{ true,  "LET p = TRUE;  LET q = FALSE; LET r = TRUE;   QUERY p -> q -> r." },
			{ true,  "LET p = TRUE;  LET q = FALSE; LET r = FALSE;  QUERY p -> q -> r." },
			{ true,  "LET p = FALSE; LET q = TRUE;  LET r = TRUE;   QUERY p -> q -> r." },
			{ true,  "LET p = FALSE; LET q = TRUE;  LET r = FALSE;  QUERY p -> q -> r." },
			{ true,  "LET p = FALSE; LET q = FALSE; LET r = TRUE;   QUERY p -> q -> r." },
			{ true,  "LET p = FALSE; LET q = FALSE; LET r = FALSE;  QUERY p -> q -> r." },

			{ true,  "LET p = TRUE;  LET q = TRUE;  QUERY p <=> q." },
			{ false, "LET p = TRUE;  LET q = FALSE; QUERY p <=> q." },
			{ false, "LET p = FALSE; LET q = TRUE;  QUERY p <=> q." },
			{ true,  "LET p = FALSE; LET q = FALSE; QUERY p <=> q." },

			{ true,  "LET p = TRUE;  LET q = TRUE;  LET r = TRUE;   QUERY p <=> q <=> r." },
			{ false, "LET p = TRUE;  LET q = TRUE;  LET r = FALSE;  QUERY p <=> q <=> r." },
			{ false, "LET p = TRUE;  LET q = FALSE; LET r = TRUE;   QUERY p <=> q <=> r." },
			{ true,  "LET p = TRUE;  LET q = FALSE; LET r = FALSE;  QUERY p <=> q <=> r." },
			{ false, "LET p = FALSE; LET q = TRUE;  LET r = TRUE;   QUERY p <=> q <=> r." },
			{ true,  "LET p = FALSE; LET q = TRUE;  LET r = FALSE;  QUERY p <=> q <=> r." },
			{ true,  "LET p = FALSE; LET q = FALSE; LET r = TRUE;   QUERY p <=> q <=> r." },
			{ false, "LET p = FALSE; LET q = FALSE; LET r = FALSE;  QUERY p <=> q <=> r." },

			{ true,  "LET p = TRUE;  QUERY p." },
			{ false, "LET p = FALSE; QUERY p." },
			{ false, "LET p = TRUE;  QUERY ~p." },
			{ true,  "LET p = FALSE; QUERY ~p." },
			{ true,  "LET p = TRUE;  QUERY ~(~p)." },
			{ false, "LET p = FALSE; QUERY ~(~p)." },
			{ true,  "LET p = TRUE;  QUERY (p)." },
			{ false, "LET p = FALSE; QUERY (p)." },
			{ true,  "LET p = TRUE;  QUERY ((p))." },
			{ false, "LET p = FALSE; QUERY ((p))." },

			{ true,  "LET p = TRUE;  LET q = TRUE;  QUERY ~(p & q) <=> (~p | ~q)."},
			{ true,  "LET p = TRUE;  LET q = FALSE; QUERY ~(p & q) <=> (~p | ~q)."},
			{ true,  "LET p = FALSE; LET q = TRUE;  QUERY ~(p & q) <=> (~p | ~q)."},
			{ true,  "LET p = FALSE; LET q = FALSE; QUERY ~(p & q) <=> (~p | ~q)."},

			{ true,  "LET p = TRUE;  LET q = TRUE;  QUERY (p -> q) <=> (~p | q)."},
			{ true,  "LET p = TRUE;  LET q = FALSE; QUERY (p -> q) <=> (~p | q)."},
			{ true,  "LET p = FALSE; LET q = TRUE;  QUERY (p -> q) <=> (~p | q)."},
			{ true,  "LET p = FALSE; LET q = FALSE; QUERY (p -> q) <=> (~p | q)."},

			{ true,  "LET p = TRUE;  LET q = TRUE;  QUERY p -> q <=> ~p | q."},
			{ true,  "LET p = TRUE;  LET q = FALSE; QUERY p -> q <=> ~p | q."},
			{ true,  "LET p = FALSE; LET q = TRUE;  QUERY p -> q <=> ~p | q."},
			{ true,  "LET p = FALSE; LET q = FALSE; QUERY p -> q <=> ~p | q."},

			{ true,  "LET p = TRUE;  LET q = p;  QUERY q." },
			{ false, "LET p = FALSE; LET q = p;  QUERY q." },
			{ false, "LET p = TRUE;  LET q = ~p; QUERY q." },
			{ true,  "LET p = FALSE; LET q = ~p; QUERY q." },

			{ false, "LET p = TRUE;  LET q = TRUE;  LET r = ~p & ~q; QUERY r." },
			{ false, "LET p = TRUE;  LET q = FALSE; LET r = ~p & ~q; QUERY r." },
			{ false, "LET p = FALSE; LET q = TRUE;  LET r = ~p & ~q; QUERY r." },
			{ true,  "LET p = FALSE; LET q = FALSE; LET r = ~p & ~q; QUERY r." },

			{ true,  "LET red = TRUE;     QUERY red." },
			{ false, "LET orange = FALSE; QUERY orange." },
			{ false, "LET yellow = TRUE;  QUERY ~yellow." },
			{ true,  "LET green = FALSE;  QUERY ~green." },
			{ true,  "LET blue = TRUE;    QUERY ~(~blue)." },
			{ false, "LET purple = FALSE; QUERY ~(~purple)." },

			{ true,  "let p = true;     query p." },
			{ true,  "let P = true;     query P." },
			{ true,  "let p = true;     query P." },
			{ true,  "let P = true;     query p." },
		});
	}

	@Parameter(value=1) public static String input;
	@Parameter(value=0) public static boolean solution;

	@Test
	public void testProgram() {
		try {
			assertEquals(solution, Parser.program(input));
		}
		catch (Exception e) {
			fail("Unhandled exception: " + e);
		}
	}

	public static void main(String args[]) throws Exception{
		PracticumTests t = new PracticumTests();
		t.testProgram();
	}

}
