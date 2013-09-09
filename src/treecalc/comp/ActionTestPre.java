package treecalc.comp;

import java.io.IOException;

import treecalc.comp.java.ActionJava;
import treecalc.comp.vm.ActionVm;

public class ActionTestPre {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ActionJava.doit("./test-resources/testinput.tcs", "gen.testinput", "./test/gen/testinput", false, false);
		ActionJava.doit("./test-resources/testinputmultiple.tcs", "gen.testinputmultiple", "./test/gen/testinputmultiple", false, false);
		ActionJava.doit("./test-resources/testnodes.tcs", "gen.testnodes", "./test/gen/testnodes", false, false);
		ActionJava.doit("./test-resources/testnodestimes.tcs", "gen.testnodestimes", "./test/gen/testnodestimes", false, false);
		ActionJava.doit("./test-resources/testtab.tcs", "gen.testtab", "./test/gen/testtab", false, false);
		ActionJava.doit("./test-resources/tclife.tcs", "gen.tclife", "./test/gen/tclife", false, false);

		ActionVm.doit("./test-resources/testinput.tcs", "gen.testinput", "./test/gen/testinput");
		ActionVm.doit("./test-resources/testinputmultiple.tcs", "gen.testinputmultiple", "./test/gen/testinputmultiple");
		ActionVm.doit("./test-resources/testnodes.tcs", "gen.testnodes", "./test/gen/testnodes");
		ActionVm.doit("./test-resources/testnodestimes.tcs", "gen.testnodestimes", "./test/gen/testnodestimes");
		ActionVm.doit("./test-resources/testtab.tcs", "gen.testtab", "./test/gen/testtab");
		ActionVm.doit("./test-resources/tclife.tcs", "gen.tclife", "./test/gen/tclife");
	}

}
