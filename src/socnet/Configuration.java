/* 
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of PieSpy.

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/

$Author: pjm2 $
$Id: Configuration.java,v 1.6 2004/05/10 10:28:06 pjm2 Exp $

*/

package socnet;

import java.awt.Color;
import java.io.File;
import java.util.*;

public class Configuration implements java.io.Serializable {
    
    public String server;
    public int port;
    public String serverPassword;
    public String nick;
    public HashSet channelSet;
    
    public int outputWidth;
    public int outputHeight;
    public File outputDirectory;
    public boolean createCurrent;
    public boolean createArchive;
    public boolean createRestorePoints;
    
    public Color backgroundColor;
    public Color channelColor;
    public Color labelColor;
    public Color titleColor;
    public Color nodeColor;
    public Color edgeColor;
    public Color borderColor;
    
    public String password;
    
    public HashSet ignoreSet;
    
    public double temporalDecayAmount;
    public int springEmbedderIterations;
    public double k;
    public double c;
    public double maxRepulsiveForceDistance;
    public double maxNodeMovement;
    public double minDiagramSize;
    public int borderSize;
    public int nodeRadius;
    public double edgeThreshold;
    public boolean showEdges;
    public boolean verbose;
    public String encoding;
    
    private Properties properties;
    
    public Configuration(Properties p) throws NoSuchElementException {
        properties = p;
        
        
        outputWidth = getInt("OutputWidth");
        outputHeight = getInt("OutputHeight");
        outputDirectory = getFile("OutputDirectory");
        createCurrent = getBoolean("CreateCurrent");
        createArchive = getBoolean("CreateArchive");
        createRestorePoints = getBoolean("CreateRestorePoints");
        
        backgroundColor = getColor("BackgroundColor");
        channelColor = getColor("ChannelColor");
        labelColor = getColor("LabelColor");
        titleColor = getColor("TitleColor");
        nodeColor = getColor("NodeColor");
        edgeColor = getColor("EdgeColor");
        borderColor = getColor("BorderColor");
        
        password = getString("Password");
        
        ignoreSet = getSet("IgnoreSet");
        
        temporalDecayAmount = getDouble("TemporalDecayAmount");
        springEmbedderIterations = getInt("SpringEmbedderIterations");
        k = getDouble("K");
        c = getDouble("C");
        maxRepulsiveForceDistance = getDouble("MaxRepulsiveForceDistance");
        maxNodeMovement = getDouble("MaxNodeMovement");
        minDiagramSize = getDouble("MinDiagramSize");
        borderSize = getInt("BorderSize");
        nodeRadius = getInt("NodeRadius");
        edgeThreshold = getDouble("EdgeThreshold");
        showEdges = getBoolean("ShowEdges");
        verbose = getBoolean("Verbose");
        encoding = getString("Encoding");
        
    }
    
    public int getInt(String label) throws NoSuchElementException {
        String value = getString(label);
        return Integer.parseInt(value);
    }
    
    public double getDouble(String label) throws NoSuchElementException {
        String value = getString(label);
        return Double.parseDouble(value);
    }
    
    public File getFile(String label) throws NoSuchElementException {
        String value = getString(label);
        return new File(value);
    }

    public boolean getBoolean(String label) {
        String value = getString(label);
        return Boolean.valueOf(value).booleanValue();
    }
    
    public Color getColor(String label) {
        String value = getString(label);
        Color color = Color.decode(value);
        return color;
    }
    
    public HashSet getSet(String label) {
        String values = getString(label);
        String[] tokens = values.split(",");
        HashSet set = new HashSet();
        for (int i = 0; i < tokens.length; i++) {
            set.add(tokens[i].trim().toLowerCase());
        }
        return set;
    }
    
    public String getString(String label) throws NoSuchElementException {
        String value = properties.getProperty(label);
        if (value == null) {
            throw new NoSuchElementException("Config did not contain: " + label);
        }
        return value;
    }
    
    
}