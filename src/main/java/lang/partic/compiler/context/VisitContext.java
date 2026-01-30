package lang.partic.compiler.context;

import lang.partic.compiler.manager.ImportManager;

public class VisitContext {
    private final ImportManager importManager;

    public VisitContext() {
        this.importManager = new ImportManager();
    }

    public ImportManager getImportManager() {
        return importManager;
    }
}
