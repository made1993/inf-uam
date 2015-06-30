----------------------------------------------------------------------
-- Fichero: MemDataMIPS.vhd
-- Descripción: Memoria de datos para el MIPS
-- Fecha última modificación: 2013-02-27

-- Autores: Luis Cayola, Roberto García Teodoro
-- Asignatura: E.C. 1º grado
-- Grupo de Prácticas:2111
-- Grupo de Teoría:211
-- Práctica: 5
-- Ejercicio: 1
----------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;

entity MemDataMIPS is
	port (
		Clk : in std_logic;
		NRst : in std_logic;
		MemDataAddr : in std_logic_vector(31 downto 0);
		MemDataDataWrite : in std_logic_vector(31 downto 0);
		MemDataWE : in std_logic;
		MemDataDataRead : out std_logic_vector(31 downto 0)
	);
end MemDataMIPS;

architecture Simple of MemDataMIPS is

  -- 4 GB son 1 gigapalabras, pero el simulador no deja tanta memoria
  -- Dejamos 64 kB (16 kpalabras), usamos los 16 LSB
  type Memoria is array (0 to (2**14)-1) of std_logic_vector(31 downto 0);
  signal memData : Memoria;

begin

	EscrituraMemProg: process(Clk, NRst)
	begin
	if NRst = '0' then
		-- Se inicializa a ceros, salvo los valores de las direcciones que
		-- tengan un valor inicial distinto de cero (datos ya cargados en
		-- memoria de datos desde el principio)
		for i in 0 to (2**14)-1 loop
			memData(i) <= (others => '0');
		end loop;
		-- Valores traídos del .bin
		-- Cada palabra ocupa 4 bytes
		memData(conv_integer(X"00002000")/4) <= X"00000001";
		memData(conv_integer(X"00002004")/4) <= X"00000002";
		memData(conv_integer(X"00002008")/4) <= X"00000003";
		memData(conv_integer(X"0000200c")/4) <= X"00000004";
		memData(conv_integer(X"00002010")/4) <= X"00000005";
		memData(conv_integer(X"00002014")/4) <= X"00000006";
		memData(conv_integer(X"00002018")/4) <= X"00000007";
		memData(conv_integer(X"0000201c")/4) <= X"00000008";
		memData(conv_integer(X"00002020")/4) <= X"00000009";
		memData(conv_integer(X"00002024")/4) <= X"0000000a";
		memData(conv_integer(X"00002028")/4) <= X"fffffffb";
		memData(conv_integer(X"0000202c")/4) <= X"ffffffff";
		memData(conv_integer(X"00002030")/4) <= X"00000007";
		memData(conv_integer(X"00002034")/4) <= X"00000009";
		memData(conv_integer(X"00002038")/4) <= X"0000000a";
		memData(conv_integer(X"0000203c")/4) <= X"fffffffa";
		memData(conv_integer(X"00002040")/4) <= X"00000014";
		memData(conv_integer(X"00002044")/4) <= X"0000000c";
		memData(conv_integer(X"00002048")/4) <= X"ffffffff";
		memData(conv_integer(X"0000204c")/4) <= X"00000007";
		memData(conv_integer(X"00002050")/4) <= X"0000000b";
		memData(conv_integer(X"00002054")/4) <= X"00000004";
		memData(conv_integer(X"00002058")/4) <= X"fffffff5";
		memData(conv_integer(X"0000205c")/4) <= X"fffffff2";
		memData(conv_integer(X"00002060")/4) <= X"fffffff1";
		memData(conv_integer(X"00002064")/4) <= X"00000012";
		memData(conv_integer(X"00002068")/4) <= X"ffffffdf";
		memData(conv_integer(X"0000206c")/4) <= X"fffffff0";
		memData(conv_integer(X"00002070")/4) <= X"0000000b";
		memData(conv_integer(X"00002074")/4) <= X"fffffffc";
		memData(conv_integer(X"00002078")/4) <= X"0000000a";

	elsif rising_edge(Clk) then
		-- En este caso se escribe por flanco de bajada para que sea
		-- a mitad de ciclo y todas las señales estén estables
		if MemDataWE = '1' then
			memData(conv_integer(MemDataAddr)/4) <= MemDataDataWrite;
		end if;
	end if;
	end process EscrituraMemProg;

	-- Lectura combinacional siempre activa
	-- Cada vez se devuelve una palabra completa, que ocupa 4 bytes
	LecturaMemProg: process(MemDataAddr, memData)
	begin
		-- Parte baja de la memoria sí está, se lee tal cual
		if MemDataAddr(31 downto 16)=X"0000" then
			MemDataDataRead <= MemData(conv_integer(MemDataAddr)/4);
		else -- Parte alta no existe, se leen ceros
			MemDataDataRead <= (others => '0');
		end if;
	end process LecturaMemProg;

end Simple;

