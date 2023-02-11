// Teoria Współbieżnośi, implementacja problemu 5 filozofów w node.js
// Opis problemu: http://en.wikipedia.org/wiki/Dining_philosophers_problem
//   https://pl.wikipedia.org/wiki/Problem_ucztuj%C4%85cych_filozof%C3%B3w
// 1. Dokończ implementację funkcji podnoszenia widelca (Fork.acquire).
// 2. Zaimplementuj "naiwny" algorytm (każdy filozof podnosi najpierw lewy, potem
//    prawy widelec, itd.).
// 3. Zaimplementuj rozwiązanie asymetryczne: filozofowie z nieparzystym numerem
//    najpierw podnoszą widelec lewy, z parzystym -- prawy. 
// 4. Zaimplementuj rozwiązanie z kelnerem (według polskiej wersji strony)
// 5. Zaimplementuj rozwiążanie z jednoczesnym podnoszeniem widelców:
//    filozof albo podnosi jednocześnie oba widelce, albo żadnego.
// 6. Uruchom eksperymenty dla różnej liczby filozofów i dla każdego wariantu
//    implementacji zmierz średni czas oczekiwania każdego filozofa na dostęp 
//    do widelców. Wyniki przedstaw na wykresach.
const fs = require("fs");
const Fork = function() {
    this.state = 0;
    return this;
}

Fork.prototype.acquire = function(cb) { 
    // zaimplementuj funkcję acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwszą próbą podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy próba jest nieudana, zwiększa czas oczekiwania dwukrotnie
    //    i ponawia próbę, itd.
    const fork = this;
    let delay = 1;
    const maxDelay = 500
    function acquireFork() {
        if(fork.state == 0){
            fork.state = 1;
		    cb();
        }
        else {
            delay *= 2;
            setTimeout(acquireFork, Math.min(delay, maxDelay));
        }
    }

    setTimeout(acquireFork, delay);
}

Fork.prototype.release = function() { 
    this.state = 0; 
}

const Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    this.sumWaitingTime = 0;
    return this;
}

Philosopher.prototype.startNaive = function(count) {
    const forks = this.forks
    const f1 = this.f1
    const f2 = this.f2
    const id = this.id
    
    // zaimplementuj rozwiązanie naiwne
    // każdy filozof powinien 'count' razy wykonywać cykl
    // podnoszenia widelców -- jedzenia -- zwalniania widelców

    const recNaive = (count) =>{
        if (count > 0){
            forks[f1].acquire(()=>{
                console.log('Philosopher ' + id + ' raised left fork')
                forks[f2].acquire(()=>{
                    console.log('Philosopher ' + id + ' raised right fork')
                    setTimeout(()=>{
                        console.log('Philosopher ' + id + ' finished eating, times to eat left: ' + (count - 1));
                        forks[f1].release()
                        forks[f2].release()
                        recNaive(count - 1)
                    },Math.floor(Math.random() * 10 + 10))
                })
            })
        }
    }
    setTimeout(recNaive, Math.floor(Math.random() * 10), count);
}

Philosopher.prototype.startAsym = function(count) {
    const forks = this.forks
    const f1 = this.id % 2 === 0 ? this.f1 : this.f2
    const f2 = this.id % 2 === 0 ? this.f2 : this.f1
    const id = this.id
    const n = this.forks.length
    let startTime = 0,endTime = 0, sumWaitingTime = this.sumWaitingTime
    // zaimplementuj rozwiązanie asymetryczne
    // każdy filozof powinien 'count' razy wykonywać cykl
    // podnoszenia widelców -- jedzenia -- zwalniania widelców
    const recAsym = (count) =>{
        if (count > 0){
            startTime = new Date().getTime()
            forks[f1].acquire(()=>{
                console.log('Philosopher ' + id + ' raised first fork')
                forks[f2].acquire(()=>{
                    console.log('Philosopher ' + id + ' raised second fork')
                    endTime = new Date().getTime()
                    sumWaitingTime += (endTime - startTime)
                    setTimeout(()=>{
                        console.log('Philosopher ' + id + ' finished eating, times to eat left: ' + (count - 1));
                        forks[f1].release()
                        forks[f2].release()
                        recAsym(count - 1)
                    },10)
                })
            })
        }else{
            const time = sumWaitingTime/meals
            console.log("Id: " + this.id + " Average waiting time: " + time)
            fs.appendFile(n + "asymmetric.txt", id.toString() + '\n' + time.toString() + '\n', "utf8", (error, data) => {
                console.log("Write complete");
            });
        }
    }
    fs.writeFile(n + "asymmetric.txt", '', "utf8", (error, data) => {});
    recAsym(count)
}

const Waiter = function(value) {
    this.state = value;
    return this;
}

Waiter.prototype.acquire = function(cb){
    const waiter = this
    let delay = 1
    const maxDelay = 500
    function acquireFork() {
        if(waiter.state > 0){
            waiter.state--;
		    cb();
        }
        else {
            delay *= 2;
            setTimeout(acquireFork, Math.min(delay, maxDelay));
        }
    }

    setTimeout(acquireFork, delay);
}

Waiter.prototype.release = function(){
    this.state++; 
}
Philosopher.prototype.startConductor = function(count) {
    const forks = this.forks
    const f1 = this.f1
    const f2 = this.f2
    const id = this.id
    const n = this.forks.length
    const waiter = waiters[n];
    let startTime = 0,endTime = 0, sumWaitingTime = this.sumWaitingTime
    
    // zaimplementuj rozwiązanie z kelnerem
    // każdy filozof powinien 'count' razy wykonywać cykl
    // podnoszenia widelców -- jedzenia -- zwalniania widelców

    const recConductor = (count) =>{
        if (count > 0){
            startTime = new Date().getTime()
            waiter.acquire(()=>{
                console.log('Philosopher ' + id + ' is making order')
                forks[f1].acquire(()=>{
                    console.log('Philosopher ' + id + ' raised first fork')
                    forks[f2].acquire(()=>{
                        console.log('Philosopher ' + id + ' raised second fork')
                        endTime = new Date().getTime()
                        sumWaitingTime += (endTime - startTime)
                        setTimeout(()=>{
                            console.log('Philosopher ' + id + ' finished eating, times to eat left: ' + (count - 1));
                            forks[f1].release()
                            forks[f2].release()
                            waiter.release()
                            recConductor(count - 1)
                        },10)
                    })
                })
            })
        }else{
            const time = sumWaitingTime/meals
            console.log("Id: " + this.id + " Average waiting time: " + time)
            fs.appendFile(n + "conductor.txt", id.toString() + '\n' + time.toString() + '\n', "utf8", (error, data) => {
                console.log("Write complete");
            });
        }
    }
    fs.writeFile(n + "conductor.txt", '', "utf8", (error, data) => {});
    recConductor(count)
}


// TODO: wersja z jednoczesnym podnoszeniem widelców
// Algorytm BEB powinien obejmować podnoszenie obu widelców, 
// a nie każdego z osobna

function acquireSimultaneous(f1, f2, cb) {
    let delay = 1
    const maxDelay = 500

    function acquireForks() {
        if(f1.state === 0 && f2.state === 0){
            f1.state = 1;
            f2.state = 1;
		    cb();
        }
        else {
            delay *= 2;
            setTimeout(acquireForks, Math.min(delay, maxDelay));
        }
    }

    setTimeout(acquireForks, delay);
}

Philosopher.prototype.startSimultaneous = function(count){
    const forks = this.forks
    const f1 = this.f1
    const f2 = this.f2
    const id = this.id
    const n = this.forks.length
    let startTime = 0, endTime = 0, sumWaitingTime = this.sumWaitingTime


    const recSimultaneous = (count)=>{
        if (count > 0){
            startTime = new Date().getTime()
            acquireSimultaneous(forks[f1], forks[f2], ()=>{
                endTime = new Date().getTime()
                sumWaitingTime += (endTime - startTime)
                setTimeout(()=>{
                    console.log('Philosopher ' + id + ' finished eating, times to eat left: ' + (count - 1));
                    forks[f1].release()
                    forks[f2].release()
                    recSimultaneous(count - 1)
                },10)
            })
        }
        else{
            // Zapis średnich czasów do pliku
            const time = sumWaitingTime/meals
            console.log("Id: " + this.id + " Average waiting time: " + time)
            fs.appendFile(n + "simultaneous.txt", id.toString() + '\n' + time.toString() + '\n', "utf8", (error, data) => {});
        }
    };

    // usuwanie początkowej zawartości pliku
    fs.writeFile(n + "simultaneous.txt", '', "utf8", (error, data) => {
        console.log("Write complete");
    });

    recSimultaneous(count)
}

const N = [5, 10];
const waiters = {}
const meals = 50

const methods = {
    1: (philosopher)=>{philosopher.startAsym(meals)},
    2: (philosopher)=>{philosopher.startConductor(meals)},
    3: (philosopher)=>{philosopher.startSimultaneous(meals)}
}
for (const n of N){
    waiters[n] = new Waiter(n - 1)
    for (const method of Object.values(methods)) {
        const forks = [];
        const philosophers = []
        for (let i = 0; i < n; i++) {
            forks.push(new Fork());
        }

        for (let i = 0; i < n; i++) {
            philosophers.push(new Philosopher(i, forks));
        }

        for (let i = 0; i < n; i++) {
            method(philosophers[i]);
        }
    }
}
