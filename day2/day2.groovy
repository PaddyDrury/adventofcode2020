assert countValidPasswords1("testinput.txt") == 2
assert countValidPasswords2("testinput.txt") == 1

def count1 = countValidPasswords1("input.txt")
def count2 = countValidPasswords2("input.txt")

println count1
println count2

def countValidPasswords1(path) {
    (path as File).readLines().findAll {checkPassword1(it)}.size()
}

def countValidPasswords2(path) {
    (path as File).readLines().findAll {checkPassword2(it)}.size()
}

def checkPassword1(String passwordLine) {

    println "Checking password $passwordLine"

    (passwordLine =~ /(?<min>\d+)-(?<max>\d+) (?<letter>[a-z]):\s(?<password>[a-z]+)/).find { match, min, max, letter, password ->
        def regex = /^([^${letter}]*$letter[^$letter]*){$min,$max}$/
        def matches = password.matches(regex)

        println "$password ${matches ? "matches" : "doesn't match"} $regex"
        matches
    }
}

def checkPassword2(String passwordLine) {

    println "Checking password $passwordLine"

    (passwordLine =~ /(?<min>\d+)-(?<max>\d+) (?<letter>[a-z]):\s(?<password>[a-z]+)/).find { match, pos1, pos2, letter, password ->
        def matches = (password as String).getAt(pos1.toInteger()-1) == letter ^ (password as String).getAt(pos2.toInteger()-1) == letter

        println "$password ${matches ? "matches" : "doesn't match"}"
        matches
    }
}