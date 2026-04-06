Controller {
    var <engine;
    var <ccBindings;
    var <noteBindings;
    var <oscBindings;

    *new { |engine|
        ^super.new.init(engine)
    }

    init { |anEngine|
        engine = anEngine;
        ccBindings = IdentityDictionary.new;
        noteBindings = IdentityDictionary.new;
        oscBindings = IdentityDictionary.new;
        ^this
    }

    bindCC { |ccNum, action, channel = nil|
        var key = [ccNum, channel];
        ccBindings[key] = MIDIdef.cc(
            ("sk_cc_" ++ ccNum ++ "_" ++ (channel ? \any)).asSymbol,
            { |val, num, chan, src|
                action.value(val, num, chan, src, engine)
            },
            ccNum,
            channel
        );
        ^this
    }

    bindNoteOn { |noteNum, action, channel = nil|
        var key = [noteNum, channel];
        noteBindings[key] = MIDIdef.noteOn(
            ("sk_note_" ++ noteNum ++ "_" ++ (channel ? \any)).asSymbol,
            { |vel, num, chan, src|
                action.value(vel, num, chan, src, engine)
            },
            noteNum,
            channel
        );
        ^this
    }

    freeAll {
        ccBindings.valuesDo(_.free);
        noteBindings.valuesDo(_.free);
        oscBindings.valuesDo(_.free);

        ccBindings.clear;
        noteBindings.clear;
        oscBindings.clear;

        ^this
    }
}