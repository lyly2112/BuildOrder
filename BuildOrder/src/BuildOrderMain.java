import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BuildOrderMain {

	static List<String> buildOrder(String[][] processes, String[] projects, List<String> resultList) {
		Set<String> tempMarks = new HashSet<String>();
		Set<String> permMarks = new HashSet<String>();

		for (int i = 0; i < processes.length; i++) {
			// check on all projects with dependencies
			if (!permMarks.contains(processes[i][0])) {
				visit(processes[i][0], processes[i][1], processes, tempMarks, permMarks, resultList);
			}
		}

		// add projects with no dependencies to the result list
		for (String project : projects) {
			if (!resultList.contains(project)) {
				resultList.add(project);
			}
		}
		return resultList;
	}

	public static void visit(String processProject, String processDep, String[][] processes, Set<String> tempMarks,
			Set<String> permMarks, List<String> resultList) {

		if (tempMarks.contains(processDep))
			throw new RuntimeException("project cannot be built");

		if (!permMarks.contains(processProject)) {
			tempMarks.add(processDep);
			tempMarks.add(processProject);

			for (int i = 0; i < processes.length; i++) {
				if (processes[i][0] == processDep) {
					visit(processDep, processes[i][1], processes, tempMarks, permMarks, resultList);
				}
			}

			if (!permMarks.contains(processDep)) {
				permMarks.add(processDep);
				tempMarks.remove(processDep);
				resultList.add(processDep);
			}

			permMarks.add(processProject);
			tempMarks.remove(processProject);
			resultList.add(processProject);
		}
	}

	public static void main(String[] args) {
		String[] projects = { "a", "b", "c", "d", "e", "f" };
		String[][] processes = { { "a", "d" }, { "f", "b" }, { "b", "d" }, { "f", "a" }, { "d", "c" } };

// 		this projects will not be able to built
//		String[][] processesFailBuild = { { "a", "d" }, { "f", "b" }, { "b", "d" }, { "f", "a" }, { "d", "a" } };

		List<String> resultList = new ArrayList<String>();

		System.out.println("projects build order:");
		System.out.println(buildOrder(processes, projects, resultList));

	}
}
