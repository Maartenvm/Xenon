/*
 * Copyright 2013 Netherlands eScience Center
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.esciencecenter.octopus.examples.files;

import java.net.URI;

import nl.esciencecenter.octopus.Octopus;
import nl.esciencecenter.octopus.OctopusFactory;
import nl.esciencecenter.octopus.credentials.Credential;
import nl.esciencecenter.octopus.credentials.Credentials;
import nl.esciencecenter.octopus.files.AbsolutePath;
import nl.esciencecenter.octopus.files.FileSystem;
import nl.esciencecenter.octopus.files.Files;
import nl.esciencecenter.octopus.files.RelativePath;

/**
 * A simple example of how to check if a given file exists. 
 * 
 * This example assumes the user provides a path to check. 
 * 
 * @author Jason Maassen <J.Maassen@esciencecenter.nl>
 * @version 1.0
 * @since 1.0
 */
public class FileExists {

    public static void main(String [] args) { 
        
        if (args.length != 1) { 
            System.out.println("Example required an absolute file path as a parameter!");
            System.exit(1);
        }
        
        String filename = args[0];
        
        try { 
            // We create a new octopus using the OctopusFactory (without providing any properties).
            Octopus octopus = OctopusFactory.newOctopus(null);

            // Next, we retrieve the Files and Credentials interfaces
            Files files = octopus.files();
            Credentials credentials = octopus.credentials();
            
            // Next we create a FileSystem 
            URI uri = new URI("file://localhost/");
            Credential c = credentials.getDefaultCredential("file");  
            FileSystem fs = files.newFileSystem(uri, c, null);
            
            // We now create an AbsolutePath representing the file
            AbsolutePath path = files.newPath(fs, new RelativePath(filename)); 
            
            // Check if the file exists 
            if (files.exists(path)) { 
                System.out.println("File " + filename + " exist!");
            } else { 
                System.out.println("File " + filename + " does not exist!");
            }
            
            // If we are done we need to close the FileSystem
            files.close(fs);
            
            // Finally, we end octopus to release all resources 
            OctopusFactory.endOctopus(octopus);

        } catch (Exception e) { 
            System.out.println("CreatingFileSystem example failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}