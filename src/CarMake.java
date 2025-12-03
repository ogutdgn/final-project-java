public enum CarMake {
    Toyota(new String[]{"Corolla", "Camry", "RAV4", "Tacoma", "Tundra", "Highlander", "Prius"}),
    Honda(new String[]{"Civic", "Accord", "CR-V", "HR-V", "Pilot", "Ridgeline", "Odyssey"}),
    Nissan(new String[]{"Altima", "Sentra", "Rogue", "Frontier", "Pathfinder", "Leaf", "Maxima"}),
    Ford(new String[]{"Fusion", "Mustang", "Escape", "F-150", "Ranger", "Explorer", "Expedition"}),
    Chevrolet(new String[]{"Silverado", "Colorado", "Camaro", "Corvette", "Tahoe", "Malibu", "Equinox"}),
    BMW(new String[]{"3 Series", "5 Series", "7 Series", "X1", "X3", "X5" , "M3" }),
    Mercedes_Benz(new String[]{"C-Class", "E-Class", "S-Class", "GLA", "GLC", "GLE", "AMG"}),
    Other(new String[]{""});  // <-- NEW OPTION

    private final String[] models;

    CarMake(String[] models) {
        this.models = models;
    }

    public String[] getModels() {
        return models;
    }

    @Override
    public String toString() {
        if (this == Other) {
            return ""; 
        }

        
        String name = name().replace("_", "-");
        String[] parts = name.split("-");
        StringBuilder display = new StringBuilder();
        for (String part : parts) {
            display.append(part.charAt(0))
                    .append(part.substring(1).toLowerCase())
                    .append("-");
        }
        display.setLength(display.length() - 1); 
        return display.toString();
    }

}
