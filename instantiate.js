const axios = require('axios').default;

const randomNames = [
  'Jeannine',
  'Hanna',
  'Wendy',
  'Reeves',
  'Julian',
  'Mason',
  'Jodi',
  'Quinn',
  'Greg',
  'Brock',
  'Lorenzo',
  'Barton',
  'Karin',
  'Graham',
  'Donnie',
  'Christian',
  'Travis',
  'Bentley',
  'Malik',
  'Best',
  'Abdul',
  'Rios',
  'Angel',
  'Ball',
  'Tommy',
  'Hopkins',
  'Felton',
  'Tanner',
  'Ollie',
  'Farmer',
  'Celeste',
  'Olsen',
  'Tara',
  'Henson',
  'Lupe',
  'Ballard',
  'Coy',
  'Cooke',
  'Mike',
  'Huerta',
];

const randomLocations = [
  {city: 'MAR_DEL_PLATA', state: 'BUENOS_AIRES'},
  {city: 'BUENOS_AIRES', state: 'CABA'},
  {city: 'BAHIA_BLANCA', state: 'BUENOS_AIRES'},
  {city: 'NECOCHEA', state: 'BUENOS_AIRES'},
  {city: 'TUCUMAN', state: 'TUCUMAN'},
  {city: 'MENDOZA', state: 'MENDOZA'},
  {city: 'ROSARIO', state: 'SANTA_FE'},
  {city: 'MISIONES', state: 'MISIONES'},
  {city: 'CAMPANA', state: 'BUENOS_AIRES'},
  {city: 'SAN_RAFAEL', state: 'MENDOZA'},
  {city: 'SAN_CARLOS_DE_BARILOCHE', state: 'RIO_NEGRO'},
];

let currentUsers = {};
let currenOrders = [];
let currentrips = new Set();
let currentDrivers = {}
let startedTrips = []
let endedTrips = []

const pickAtRandom = list =>
  list[Math.floor(Math.random() * list.length)];

const generateRandomClient = () => {
  const location = pickAtRandom(randomLocations)
  return {
    name: pickAtRandom(randomNames),
    surname: pickAtRandom(randomNames),
    document: Math.floor(Math.random() * 50000000),
    city: location.city,
    state: location.state,
    // address: "",
    // phone: Math.floor(Math.random() * 50000000),
  }
}

const instance = axios.create({
  baseURL: 'http://localhost:8080/appPaquetes/resources/',
  timeout: 1000,
});

const addNewClient = async () => {
  const cli = generateRandomClient();
  try {
    process.stdout.write(`registernig ${cli.name} ${cli.surname}`); // \r makes it overritable
    const res = await instance.post('/clientes/register',null, {
      params: {
        nombre: cli.name,
        apellido: cli.surname,
      }
    });
    process.stdout.write(` --> Ok, id: ${res.data.entry.ID}\n`);
    currentUsers[res.data.entry.ID] = cli;
  } catch(e) {
    process.stdout.write(` --> Failed ${e}\n`);
  }
}

const addNewDriver = async () => {
  const cli = generateRandomClient();
  try {
    process.stdout.write(`registernig driver ${cli.name} ${cli.surname}`); // \r makes it overritable
    const res = await instance.post('/camion/altaConductor',null, {
      params: {
        nombre: cli.name,
        apellido: cli.surname,
        documento: cli.document,
        city: cli.city,
        state: cli.state
      }
    });
    process.stdout.write(` --> Ok, id: ${res.data.entry.ID}\n`);
    currentDrivers[res.data.entry.ID] = cli;
  } catch(e) {
    process.stdout.write(` --> Failed ${e}\n`);
  }
}

const addNewPackage = async () => {
  const cliId = pickAtRandom(Object.keys(currentUsers))
  const ordId = pickAtRandom(currenOrders)
  try {
    process.stdout.write(`creating package`); // \r makes it overritable
    const res = await instance.post('pedidos/createPackage',null, {
      params: {
        recipientID: cliId,
        orderID:ordId,
        cost: Math.random() * 100,
        weight: Math.random() * 100,
        origin: pickAtRandom(randomLocations).city,
        destination: pickAtRandom(randomLocations).city
      }
    });
    process.stdout.write(` --> Ok, id: ${res.data.entry.ID}, trip: ${res.data.entry.trip[0].ID}\n`);
    currentrips.add(res.data.entry.trip[0].ID)
  } catch(e) {
    process.stdout.write(` --> Failed ${e}\n`);
  }
}

const addNewOrder = async () => {
  const cliId = pickAtRandom(Object.keys(currentUsers))
  try {
    process.stdout.write(`creating order`); // \r makes it overritable
    const res = await instance.post('pedidos/create',null, {
      params: {
        clientId: cliId,
      }
    });
    process.stdout.write(` --> Ok, id: ${res.data.entry.ID}\n`);
    currenOrders = [...currenOrders, res.data.entry.ID]
  } catch(e) {
    process.stdout.write(` --> Failed ${e}}\n`);
  }
}

const randomCharacters = 'abcdefghijknmlopqrstwxyz1234567890'.split('');

const addNewTruck = async () => {
  const randomPlate = new Array(9).fill(0).map(() => pickAtRandom(randomCharacters)).join('');
  try {
    process.stdout.write(`registernig truck`); // \r makes it overritable
    const res = await instance.post('/camion/altaCamion',null, {
      params: {
        matricula: randomPlate,
        pesoMax: Math.floor((Math.random() * 500)+700),
      }
    });
    process.stdout.write(` --> Ok, plate: ${res.data.entry.numberPlate}\n`);
  } catch(e) {
    process.stdout.write(` --> Failed ${e}\n`);
  }
}

const addNewTrailer = async () => {
  const randomPlate = new Array(9).fill(0).map(() => pickAtRandom(randomCharacters)).join('');
  try {
    process.stdout.write(`registernig truck`); // \r makes it overritable
    const res = await instance.post('/camion/altaTrailer',null, {
      params: {
        matricula: randomPlate,
        pesoMax: Math.floor((Math.random() * 500)+700),
      }
    });
    process.stdout.write(` --> Ok, plate: ${res.data.entry.numberPlate}\n`);
  } catch(e) {
    process.stdout.write(` --> Failed ${e}\n`);
  }
}


const startTrip = async () => {
  let trip = pickAtRandom([...currentrips]);
  while (startedTrips.includes(trip)) {
    trip = pickAtRandom([...currentrips]);
  }
  startedTrips = [...startedTrips, trip]
  try {
    process.stdout.write(`starting trip`); // \r makes it overritable
    await instance.post('/camion/salidaViaje',null, {
      params: {
        ID: trip,
      }
    });
    process.stdout.write(` --> Ok\n`);
  } catch(e) {
    process.stdout.write(` --> Failed ${e}\n`);
  }
}

const endTrip = async () => {
  let trip = pickAtRandom(startedTrips);
  while (endedTrips.includes(trip)) {
    trip = pickAtRandom([...startedTrips]);
  }
  endedTrips = [...endedTrips, trip]
  try {
    process.stdout.write(`ending trip`); // \r makes it overritable
    await instance.post('/camion/llegadaViaje',null, {
      params: {
        ID: trip,
      }
    });
    process.stdout.write(` --> Ok\n`);
  } catch(e) {
    process.stdout.write(` --> Failed ${e}\n`);
  }
}


const addCLients = async () => {
  console.log('adding clients:')
  for (i = 0; i<30; i++){
    await addNewClient()
  }
  for (i = 0; i<30; i++){
    await addNewDriver()
  }
  for (i = 0; i<30; i++){
    await addNewOrder()
  }
  for (i = 0; i<40; i++){
    await addNewTruck()
  }
  for (i = 0; i<40; i++){
    await addNewTrailer()
  }
  for (i = 0; i<200; i++){
    await addNewPackage()
  }
  for(i = 0; i<100; i++){
    await startTrip()
  }
  for(i = 0; i<50; i++){
    await endTrip()
  }
}
addCLients()