SKRegistry {
    classvar <patterns;
    classvar <scenes;

    *initClass {
        patterns = IdentityDictionary.new;
        scenes = IdentityDictionary.new;
    }

    *addPattern { |name, pattern|
        patterns[name] = pattern;
        ^pattern
    }

    *pattern { |name|
        ^patterns[name]
    }

    *addScene { |name, scene|
        scenes[name] = scene;
        ^scene
    }

    *scene { |name|
        ^scenes[name]
    }

    *clear {
        patterns.clear;
        scenes.clear;
    }
}