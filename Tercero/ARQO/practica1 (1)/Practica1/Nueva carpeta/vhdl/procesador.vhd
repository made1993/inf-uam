library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.STD_LOGIC_UNSIGNED.all;

entity procesador is
port(
	Clk         : in  std_logic;
	Reset       : in  std_logic;
	-- Instruction memory
	I_Addr      : out std_logic_vector(31 downto 0);
	I_DataIn    : in  std_logic_vector(31 downto 0);
	-- Data memory
	D_Addr      : out std_logic_vector(31 downto 0);
	D_WrEn     	: out std_logic;
	D_DataOut   : out std_logic_vector(31 downto 0);
	D_DataIn    : in  std_logic_vector(31 downto 0)
);
end procesador;

architecture procesador_arq of procesador is 

signal auxPc,auxRd1,auxRd2,auxWd3,auxMuxAluSrc,auxMuxJump,auxRes,auxMuxMemToReg,auxMuxPcSrc,BeqAddr1,BeqAddr2,sigExtSigno,sigExtCero,JumpAddr,auxPC4: std_logic_vector(31 downto 0);
signal auxEq, auxWe3,auxMemToReg,auxBranch,auxAluSrc,auxRegDest,auxJump,auxEqInv,auxPcSrc: std_logic;
signal auxAluControl :std_logic_vector(2 downto 0);
signal auxA3,auxMuxRegDest: std_logic_vector(4 downto 0);


	
	
component ALUMIPS --definimos la ALU
	port (
		Op1: in std_logic_vector(31 downto 0); -- Operando
		Op2: in std_logic_vector(31 downto 0); -- Operando
		ALUControl: in std_logic_vector(2 downto 0); -- Operacion
		Res: out std_logic_vector(31 downto 0) -- Resultado
		--Z: out std_logic -- Bandera
	);
end component;




component UnidadControlMIPS
	port (
		OPCode : in std_logic_vector(31 downto 26);
		Funct : in std_logic_vector(5 downto 0);
		MemToReg : out std_logic;
		MemWrite : out std_logic;
		Branch : out std_logic;
		ALUControlUC : out std_logic_vector(2 downto 0);
		ALUSrc : out std_logic;
		RegDest : out std_logic;
		RegWrite : out std_logic;
		--RegToPc : out std_logic;
		--ExtCero : out std_logic;
		Jump : out std_logic
		--PCToReg : out std_logic
	);
	end component;
	



component RegsMIPS
		port (
		Clk : in std_logic; -- Reloj
		Reset : in std_logic; -- Reset asíncrono a nivel bajo
		A1 : in std_logic_vector(4 downto 0); -- Dirección para el puerto Rd1
		Rd1 : out std_logic_vector(31 downto 0); -- Dato del puerto Rd1
		A2 : in std_logic_vector(4 downto 0); -- Dirección para el puerto Rd2
		Rd2 : out std_logic_vector(31 downto 0); -- Dato del puerto Rd2
		A3 : in std_logic_vector(4 downto 0); -- Dirección para el puerto Wd3
		Wd3 : in std_logic_vector(31 downto 0); -- Dato de entrada Wd3
		We3 : in std_logic -- Habilitación del banco de registros
	);
	end component;


begin



--Registro contador de programa (PC). Se incrementa en 4 cada ciclo de reloj. El Reset asíncrono.
process (Clk,Reset)
	begin 
		if Reset = '0' then 
			auxPc<=(others =>'0');
		elsif falling_edge (Clk) then 
			auxPc<= auxMuxJump;
		end if;
end process;
	
	I_Addr <= auxPc;
	
--Instanciación del banco de registros.
miregs: RegsMIPS
port map
(		Clk =>Clk,
		Reset =>Reset,
		A1 => I_DataIn(25 downto 21),--VOY A LEER UNA ENTRADA
		Rd1 => auxRd1, -- Dato del puerto Rd1
		A2 => I_DataIn(20 downto 16), -- Dirección para el puerto Rd2
		Rd2 => auxRd2, -- Dato del puerto Rd2
		A3 => auxA3, -- Dirección para el puerto Wd3
		Wd3 =>auxWd3, -- Dato de entrada Wd3
		We3 => auxWe3 -- Habilitación del banco de registros
		
	--LUEGO CUANDO INSTANCIE LA UC, DIRE QUE WE3 VAYA CON AUXWE3 (TENER EN CUENTA CUAL ES SALIDA Y CUAL ENTRADA)
		);


--Instanciación de la ALU.
miALU: ALUMIPS
port map
(		Op1 => auxRd1 , -- Operando
		Op2 => auxMuxAluSrc, -- Operando --ESTE TIENE RELACION CON EL ALUSRC
		ALUControl => auxAluControl, -- Operacion
		Res => auxRes -- Resultado. Será lo que le entre a D_Addr
		--Z => auxZ -- Bandera
);

--Instanciación de la unidad de Control.
miUC: UnidadControlMIPS
port map
(		OPCode=>I_DataIn(31 downto 26),
		Funct =>I_DataIn(5 downto 0),
		MemToReg => auxMemToReg,
		MemWrite => D_WrEn, --DUDA: MEMWRITE ES SALIDA PERO VA A D_WrEn,que tambien es salida???
		Branch =>auxBranch,
		ALUControlUC =>auxAluControl,
		ALUSrc=>auxAluSrc,
		RegDest =>auxRegDest,
		RegWrite =>auxWe3,
		--RegToPc=>auxRegToPc,
		--ExtCero =>auxExtCero,
		Jump =>auxJump
		--PCToReg =>auxPcToReg
);


auxPC4 <= auxPC+4;

D_DataOut <= auxRd2;

D_Addr<=auxRes; --Asignamos a D_Addr la auxiliar del resultado de la ALU

--Extensión de ceros
--sigExtCero <=x"0000" & I_DataIn(15 downto 0);

--Extensión de Signo (Si el msb es 1, extiende a 1.Si es 0, extiende a 0)
sigExtSigno<= x"0000" & I_DataIn(15 downto 0) when I_DataIn(15)='0' else x"FFFF" & I_DataIn(15 downto 0);


--Dirección para el salto condicional de la instrucción beq
BeqAddr1<= sigExtSigno(29 downto 0) & "00";
BeqAddr2<= BeqAddr1 + auxPC4;

--Dirección para jump
JumpAddr<= auxPC4(31 downto 28)& I_DataIn(25 downto 0) & "00";


--Mux de ALUSrc
auxMuxAluSrc<=auxRd2 when auxAluSrc = '0' else sigExtSigno;

--Mux de RegToPc
--auxMuxRegToPC <= auxMuxJump when auxRegToPc = '0' else auxRd1;

--Mux de MemToReg
auxWd3 <= auxRes when auxMemToReg = '0' else D_DataIn;

--Mux de PCToReg
--auxA3<= auxMuxRegDest when auxPCToReg = '0' else "11111";
--auxWd3<=auxMuxMemToReg when auxPcToReg = '0' else auxPC4;

--Mux de RegDest
auxA3<= I_DataIn(20 downto 16) when auxRegDest='0' else I_DataIn(15 downto 11);

--Puerta AND para el PCSrc 
auxPcSrc<= auxEqInv and auxBranch;
--Inversion de auxEq
auxEqInv<= (not auxEq);--inversion

--Mux de PcSrc
auxMuxPcSrc<= auxPC4 when auxPcSrc='0' else BeqAddr2;

--Mux de ExtCero
--auxMuxExtcero <= sigExtSigno when auxExtCero='0' else sigExtCero;

--Mux de Jump
auxMuxJump <=  auxMuxPcSrc when auxJump ='0' else JumpAddr;

--Señal AuxEq
auxEq <= '1' when auxRd1 = auxRd2 else '0';


end procesador_arq;
