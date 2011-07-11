------------------------------------------------------------------------
--
-- This source file may be used and distributed without restriction.
-- No declarations or definitions shall be added to this package. 
-- This package cannot be sold or distributed for profit.
--
--   ****************************************************************
--   *                                                              *
--   *                      W A R N I N G		 	    *
--   *								    *
--   *   This DRAFT version IS NOT endorsed or approved by IEEE     *
--   *								    *
--   ****************************************************************
--
-- Title:    PACKAGE MATH_REAL
--
-- Library:  This package shall be compiled into a library 
--           symbolically named IEEE.
--
-- Purpose:  VHDL declarations for mathematical package MATH_REAL
--	     which contains common real constants, common real
--	     functions, and real trascendental functions.
--
-- Author:   IEEE VHDL Math Package Study Group 
--   
-- Notes:  
-- 	The package body shall be considered the formal definition of 
-- 	the semantics of this package. Tool developers may choose to implement 
-- 	the package body in the most efficient manner available to them.
--
-- History:
-- 	Version 0.1  (Strawman) Jose A. Torres	6/22/92
-- 	Version 0.2		Jose A. Torres	1/15/93
-- 	Version	0.3		Jose A. Torres	4/13/93
--	Version 0.4		Jose A. Torres	4/19/93
--	Version 0.5		Jose A. Torres	4/20/93 Added RANDOM()
--	Version 0.6		Jose A. Torres	4/23/93 Renamed RANDOM as
--							UNIFORM.  Modified
--							rights banner.
--	Version 0.7		Jose A. Torres	5/28/93 Rev up for compatibility
--							with package body.
-------------------------------------------------------------
Library IEEE;

Package MATH_REAL is

    -- 
    -- commonly used constants
    --
    constant  MATH_E :   real := 2.71828_18284_59045_23536;  
    						  -- value of e
    constant  MATH_1_E:  real := 0.36787_94411_71442_32160;
  						  -- value of 1/e
    constant  MATH_PI :  real := 3.14159_26535_89793_23846;  
    						  -- value of pi
    constant  MATH_1_PI :  real := 0.31830_98861_83790_67154;  
    						  -- value of 1/pi
    constant  MATH_LOG_OF_2:  real := 0.69314_71805_59945_30942;
    						  -- natural log of 2
    constant  MATH_LOG_OF_10: real := 2.30258_50929_94045_68402;
    						  -- natural log of10
    constant  MATH_LOG2_OF_E:  real := 1.44269_50408_88963_4074;
    						  -- log base 2 of e
    constant  MATH_LOG10_OF_E: real := 0.43429_44819_03251_82765;
    						  -- log base 10 of e
    constant  MATH_SQRT2: real := 1.41421_35623_73095_04880; 
    						  -- sqrt of 2
    constant  MATH_SQRT1_2: real := 0.70710_67811_86547_52440; 
    						  -- sqrt of 1/2
    constant  MATH_SQRT_PI: real := 1.77245_38509_05516_02730; 
    						  -- sqrt of pi
    constant  MATH_DEG_TO_RAD: real := 0.01745_32925_19943_29577;
    			  	-- conversion factor from degree to radian
    constant  MATH_RAD_TO_DEG: real := 57.29577_95130_82320_87685;
    			   	-- conversion factor from radian to degree

    --
    -- attribute for functions whose implementation is foreign (C native)
    --
    attribute FOREIGN : string; -- predefined attribute in VHDL-1992

    --
    -- function declarations
    --
    function SIGN (X: real ) return real;
    	-- returns 1.0 if X > 0.0; 0.0 if X == 0.0; -1.0 if X < 0.0

    function CEIL (X : real ) return real;
    	-- returns smallest integer value (as real) not less than X

    function FLOOR (X : real ) return real;
    	-- returns largest integer value (as real) not greater than X

    function ROUND (X : real ) return real;
    	-- returns integer FLOOR(X + 0.5) if X > 0;
    	-- return integer CEIL(X - 0.5) if X < 0
    
    function FMAX (X, Y : real ) return real;
    	-- returns the algebraically larger of X and Y

    function FMIN (X, Y : real ) return real;
    	-- returns the algebraically smaller of X and Y

    procedure UNIFORM (variable Seed1,Seed2:inout integer; variable X:out real);
	-- returns a pseudo-random number with uniform distribution in the 
	-- interval (0.0, 1.0).
	-- Before the first call to UNIFORM, the seed values (Seed1, Seed2) must
	-- be initialized to values in the range [1, 2147483562] and 
	-- [1, 2147483398] respectively.  The seed values are modified after 
	-- each call to UNIFORM.
	-- This random number generator is portable for 32-bit computers, and
	-- it has period ~2.30584*(10**18) for each set of seed values.
	--
	-- For VHDL-1992, the seeds will be global variables, functions to 
	-- initialize their values (INIT_SEED) will be provided, and the UNIFORM
	-- procedure call will be modified accordingly.  

    function SRAND (seed: in integer ) return integer;
    	--
	-- sets value of seed for sequence of 
    	-- pseudo-random numbers.   
    	-- It uses the foreign native C function srand().
    attribute FOREIGN of SRAND : function is "C_NATIVE"; 

    --function RAND return integer;		
    	--
	-- returns an integer pseudo-random number with uniform distribution.
	-- It uses the foreign native C function rand(). 
    	-- Seed for the sequence is initialized with the
    	-- SRAND() function and value of the seed is changed every
        -- time SRAND() is called,  but it is not visible.
    	-- The range of generated values is platform dependent.
    attribute FOREIGN of RAND : function is "C_NATIVE"; 

    --function GET_RAND_MAX  return integer;		
    	--
	-- returns the upper bound of the range of the
    	-- pseudo-random numbers generated by  RAND().
    	-- The support for this function is platform dependent, and
	-- it uses foreign native C functions or constants.
	-- It may not be available in some platforms.
    	-- Note: the value of (RAND() / GET_RAND_MAX()) is a
    	--       pseudo-random number distributed between 0 & 1.
    attribute FOREIGN of GET_RAND_MAX : function is "C_NATIVE"; 

    function SQRT (X : real ) return real;
    	-- returns square root of X;  X >= 0

    function CBRT (X : real ) return real;
    	-- returns cube root of X

    function "**" (X : integer; Y : real) return real;
    	-- returns Y power of X ==>  X**Y;
    	-- error if X = 0 and Y <= 0.0
    	-- error if X < 0 and Y does not have an integer value

    function "**" (X : real; Y : real) return real;
    	-- returns Y power of X ==>  X**Y;
    	-- error if X = 0.0 and Y <= 0.0
    	-- error if X < 0.0 and Y does not have an integer value

    function EXP  (X : real ) return real;
    	-- returns e**X; where e = MATH_E

    function LOG (X : real ) return real;
    	-- returns natural logarithm of X; X > 0

    function LOG (BASE: positive; X : real) return real;
    	-- returns logarithm base BASE of X; X > 0

    function  SIN (X : real ) return real;
    	-- returns sin X; X in radians

    function  COS ( X : real ) return real;
    	-- returns cos X; X in radians

    function  TAN (X : real ) return real;
    	-- returns tan X; X in radians
    	-- X /= ((2k+1) * PI/2), where k is an integer

    function  ASIN (X : real ) return real; 
    	-- returns  -PI/2 < asin X < PI/2; | X | <= 1

    function  ACOS (X : real ) return real;
    	-- returns  0 < acos X < PI; | X | <= 1

    function  ATAN (X : real) return real;
    	-- returns  -PI/2 < atan X < PI/2

    function  ATAN2 (X : real; Y : real) return real;
    	-- returns  atan (X/Y); -PI < atan2(X,Y) < PI; Y /= 0.0

    function SINH (X : real) return real;
    	-- hyperbolic sine; returns (e**X - e**(-X))/2

    function  COSH (X : real) return real;
    	-- hyperbolic cosine; returns (e**X + e**(-X))/2

    function  TANH (X : real) return real;
    	-- hyperbolic tangent; -- returns (e**X - e**(-X))/(e**X + e**(-X))
    
    function ASINH (X : real) return real;
    	-- returns ln( X + sqrt( X**2 + 1))

    function ACOSH (X : real) return real;
    	-- returns ln( X + sqrt( X**2 - 1));   X >= 1

    function ATANH (X : real) return real;
    	-- returns (ln( (1 + X)/(1 - X)))/2 ; | X | < 1

end  MATH_REAL;

--------------------------------------------------------------- 
--
-- This source file may be used and distributed without restriction.
-- No declarations or definitions shall be added to this package.
-- This package cannot be sold or distributed for profit. 
--
--   ****************************************************************
--   *                                                              *
--   *                      W A R N I N G		 	    *
--   *								    *
--   *   This DRAFT version IS NOT endorsed or approved by IEEE     *
--   *							            *
--   ****************************************************************
--
-- Title:    PACKAGE BODY MATH_REAL
--
-- Library:  This package shall be compiled into a library 
--           symbolically named IEEE.
--
-- Purpose:  VHDL declarations for mathematical package MATH_REAL
--	     which contains common real constants, common real
--	     functions, and real trascendental functions.
--
-- Author:   IEEE VHDL Math Package Study Group 
--
-- Notes:
-- 	The package body shall be considered the formal definition of 
-- 	the semantics of this package. Tool developers may choose to implement 
-- 	the package body in the most efficient manner available to them.
--
--      Source code and algorithms for this package body comes from the 
--	following sources: 
--		IEEE VHDL Math Package Study Group participants,
--		U. of Mississippi, Mentor Graphics, Synopsys,
--		Viewlogic/Vantage, Communications of the ACM (June 1988, Vol
--		31, Number 6, pp. 747, Pierre L'Ecuyer, Efficient and Portable
--		Random Number Generators), Handbook of Mathematical Functions
--	        by Milton Abramowitz and Irene A. Stegun (Dover).
--
-- History:
-- 	Version 0.1	Jose A. Torres	4/23/93	First draft
-- 	Version 0.2	Jose A. Torres	5/28/93	Fixed potentially illegal code
-------------------------------------------------------------
Library IEEE;

Package body MATH_REAL is

    -- 
    -- some constants for use in the package body only
    --
    constant  Q_PI :   real := MATH_PI/4.0;
    constant  HALF_PI :   real := MATH_PI/2.0;
    constant  TWO_PI :   real := MATH_PI*2.0;
    constant  MAX_ITER:  integer := 27; -- max precision factor for cordic

    --
    -- some type declarations for cordic operations
    -- 
    
    constant KC : REAL := 6.0725293500888142e-01; -- constant for cordic
    type REAL_VECTOR is array (NATURAL range <>) of REAL; 
    type NATURAL_VECTOR is array (NATURAL range <>) of NATURAL; 
    subtype REAL_VECTOR_N is REAL_VECTOR (0 to max_iter);
    subtype REAL_ARR_2 is REAL_VECTOR (0 to 1); 
    subtype REAL_ARR_3 is REAL_VECTOR (0 to 2); 
    subtype QUADRANT is INTEGER range 0 to 3; 
    type CORDIC_MODE_TYPE is (ROTATION, VECTORING); 
  
    --
    -- auxiliary functions for cordic algorithms
    --
    function POWER_OF_2_SERIES (d : NATURAL_VECTOR; initial_value : REAL;
                number_of_values : NATURAL) return REAL_VECTOR is 

      variable v : REAL_VECTOR (0 to number_of_values);  
      variable temp : REAL := initial_value; 
      variable flag : boolean := true; 
    begin
      for i in 0 to number_of_values loop 
         v(i) := temp; 
         for p in d'range loop 
            if i = d(p) then 
               flag := false;
            end if;                  
         end loop; 
         if flag then
            temp := temp/2.0;
         end if; 
         flag := true;
      end loop; 
      return v;
    end POWER_OF_2_SERIES; 


   constant two_at_minus : REAL_VECTOR := POWER_OF_2_SERIES(
                                               NATURAL_VECTOR'(100, 90),1.0,
                                                                  MAX_ITER);  

   constant epsilon : REAL_VECTOR_N := (
                                        7.8539816339744827e-01,
                                        4.6364760900080606e-01,
                                        2.4497866312686413e-01,
                                        1.2435499454676144e-01,
                                        6.2418809995957351e-02,
                                        3.1239833430268277e-02,
                                        1.5623728620476830e-02,
                                        7.8123410601011116e-03,
                                        3.9062301319669717e-03,
                                        1.9531225164788189e-03,
                                        9.7656218955931937e-04,
                                        4.8828121119489829e-04,
                                        2.4414062014936175e-04,
                                        1.2207031189367021e-04,
                                        6.1035156174208768e-05,
                                        3.0517578115526093e-05,
                                        1.5258789061315760e-05,
                                        7.6293945311019699e-06,
                                        3.8146972656064960e-06,
                                        1.9073486328101870e-06,
                                        9.5367431640596080e-07,
                                        4.7683715820308876e-07,
                                        2.3841857910155801e-07,
                                        1.1920928955078067e-07,
                                        5.9604644775390553e-08,
                                        2.9802322387695303e-08,
                                        1.4901161193847654e-08,
                                        7.4505805969238281e-09
                                       );

   function CORDIC ( x0 : REAL;  
                     y0 : REAL;  
                     z0 : REAL;  
                      n : NATURAL;                 --       precision factor 
            CORDIC_MODE : CORDIC_MODE_TYPE         --      rotation (z -> 0) 
                                                   --  or vectoring (y -> 0) 
                    ) return REAL_ARR_3 is 
     variable x : REAL := x0;
     variable y : REAL := y0;
     variable z : REAL := z0;
     variable x_temp : REAL; 
   begin
      if CORDIC_MODE = ROTATION then 
         for k in 0 to n loop 
            x_temp := x;
            if ( z >= 0.0) then
               x := x - y * two_at_minus(k);
               y := y + x_temp * two_at_minus(k); 
               z := z - epsilon(k);
            else 
               x := x + y * two_at_minus(k);
               y := y - x_temp * two_at_minus(k);
               z := z + epsilon(k);
            end if; 
         end loop;
      else 
         for k in 0 to n loop 
            x_temp := x;
            if ( y < 0.0) then
               x := x - y * two_at_minus(k);
               y := y + x_temp * two_at_minus(k); 
               z := z - epsilon(k);
            else 
               x := x + y * two_at_minus(k);
               y := y - x_temp * two_at_minus(k);
               z := z + epsilon(k);
            end if; 
         end loop;
      end if;
      return REAL_ARR_3'(x, y, z);
   end CORDIC;          

    --
    -- non-trascendental functions
    --
    function SIGN (X: real ) return real is
    	-- returns 1.0 if X > 0.0; 0.0 if X == 0.0; -1.0 if X < 0.0
    begin
	   if  ( X > 0.0 )  then
		return 1.0;
	   elsif ( X < 0.0 )  then
		return -1.0;
	   else
		return 0.0;
	   end if;
    end SIGN; 

    function CEIL (X : real ) return real is
    	-- returns smallest integer value (as real) not less than X
	-- No conversion to an integer type is expected, so truncate cannot 
	-- overflow for large arguments.

         variable large: real  := 1073741824.0;
         type long is range -1073741824 to 1073741824;
         -- 2**30 is longer than any single-precision mantissa
         variable rd: real;
   
      begin
         if abs( X) >= large then
   	    return X;
         else
   	    rd := real ( long( X));
   	    if X > 0.0 then
   	    	if rd >= X then
   	       		return rd;
   	    	else
   	       		return rd + 1.0;
   	    	end if;
   	    elsif  X = 0.0  then
			return 0.0;
	    else
   	    	if rd <= X then
   	       		return rd;
   	    	else
   	       		return rd - 1.0;
   	    	end if;
   	    end if;
         end if;
      end CEIL;

    function FLOOR (X : real ) return real is
    	-- returns largest integer value (as real) not greater than X
   	-- No conversion to an integer type is expected, so truncate 
	-- cannot overflow for large arguments.
   	-- 
         variable large: real  := 1073741824.0;
         type long is range -1073741824 to 1073741824;
         -- 2**30 is longer than any single-precision mantissa
         variable rd: real;
   
      begin
         if abs( X ) >= large then
   	 	return X;
         else
   	 	rd := real ( long( X));
   	 	if X > 0.0 then
   	    		if rd <= X then
   	       			return rd;
   	    		else
   	       			return rd - 1.0;
   	    		end if;
   	 	elsif  X = 0.0  then
			return 0.0;
		else
   	    		if rd >= X then
   	       			return rd;
   	    		else
   	       			return rd + 1.0;
   	    		end if;
   	 	end if;
         end if;
      end FLOOR;

    function ROUND (X : real ) return real is
    	-- returns integer FLOOR(X + 0.5) if X > 0;
    	-- return integer CEIL(X - 0.5) if X < 0
    begin
	   if  X > 0.0  then
		return FLOOR(X + 0.5);
	   elsif  X < 0.0  then
		return CEIL( X - 0.5);
	   else
		return 0.0;
	   end if;
    end ROUND;
    
    function FMAX (X, Y : real ) return real is
    	-- returns the algebraically larger of X and Y
    begin
	if X > Y then
	   return X;
	else
	   return Y;
	end if;
    end FMAX;

    function FMIN (X, Y : real ) return real is
    	-- returns the algebraically smaller of X and Y
    begin
	if X < Y then
	   return X;
	else
	   return Y;
	end if;
    end FMIN;

    --
    -- Pseudo-random number generators
    --

    procedure UNIFORM(variable Seed1,Seed2:inout integer;variable X:out real) is
	-- returns a pseudo-random number with uniform distribution in the 
	-- interval (0.0, 1.0).
	-- Before the first call to UNIFORM, the seed values (Seed1, Seed2) must
	-- be initialized to values in the range [1, 2147483562] and 
	-- [1, 2147483398] respectively.  The seed values are modified after 
	-- each call to UNIFORM.
	-- This random number generator is portable for 32-bit computers, and
	-- it has period ~2.30584*(10**18) for each set of seed values.
	--
	-- For VHDL-1992, the seeds will be global variables, functions to 
	-- initialize their values (INIT_SEED) will be provided, and the UNIFORM
	-- procedure call will be modified accordingly.  
	
	variable z, k: integer;
	begin
	k := Seed1/53668;
	Seed1 := 40014 * (Seed1 - k * 53668) - k * 12211;
	
	if Seed1 < 0  then
		Seed1 := Seed1 + 2147483563;
	end if;


	k := Seed2/52774;
	Seed2 := 40692 * (Seed2 - k * 52774) - k * 3791;
	
	if Seed2 < 0  then
		Seed2 := Seed2 + 2147483399;
	end if;

	z := Seed1 - Seed2;
	if z < 1 then
		z := z + 2147483562;
	end if;

	X :=  REAL(Z)*4.656613e-10;
    end UNIFORM;


    function SRAND (seed: in integer ) return integer is
    	--
	-- sets value of seed for sequence of 
    	-- pseudo-random numbers.   
	-- Returns the value of the seed.
    	-- It uses the foreign native C function srand().
    begin
    end SRAND;

    function RAND return integer is
    	--
	-- returns an integer pseudo-random number with uniform distribution.
	-- It uses the foreign native C function rand(). 
    	-- Seed for the sequence is initialized with the
    	-- SRAND() function and value of the seed is changed every
        -- time SRAND() is called,  but it is not visible.
    	-- The range of generated values is platform dependent.
    begin
    end RAND;

    function GET_RAND_MAX  return integer is
    	--
	-- returns the upper bound of the range of the
    	-- pseudo-random numbers generated by  RAND().
    	-- The support for this function is platform dependent, and
	-- it uses foreign native C functions or constants.
	-- It may not be available in some platforms.
    	-- Note: the value of (RAND / GET_RAND_MAX) is a
    	--       pseudo-random number distributed between 0 & 1.
    begin
    end GET_RAND_MAX;

    --
    -- trascendental and trigonometric functions
    --

    function SQRT (X : real ) return real is
    	-- returns square root of X;  X >= 0
	--
	-- Computes square root using the Newton-Raphson approximation:
	-- F(n+1) = 0.5*[F(n) + x/F(n)];
	--

	constant inival: real := 1.5;
	constant eps : real := 0.000001;
	constant relative_err : real := eps*X;

	variable oldval : real ;
	variable newval : real ;

    begin
	-- check validity of argument
	if ( X < 0.0 ) then
		assert false report "X < 0 in SQRT(X)" 
			severity ERROR;
		return (0.0);
	end if;

	-- get the square root for special cases
	if X = 0.0 then
	  	return 0.0;
	else
		if ( X = 1.0 ) then
			return 1.0; -- return exact value
		end if;
	end if;

	-- get the square root for general cases
	oldval := inival;
	newval := (X/oldval + oldval)/2.0;

	while ( abs(newval -oldval) > relative_err ) loop
		oldval := newval;
		newval := (X/oldval + oldval)/2.0;
	end loop;

	return newval;
    end SQRT;

    function CBRT (X : real ) return real is
    	-- returns cube root of X
	-- Computes square root using the Newton-Raphson approximation:
	-- F(n+1) = (1/3)*[2*F(n) + x/F(n)**2];
	--

		constant inival: real := 1.5;
		constant eps : real := 0.000001;
		constant relative_err : real := eps*abs(X);

		variable xlocal : real := X;
		variable negative : boolean := X < 0.0;
		variable oldval : real ;
		variable newval : real ;

    begin 
		
		-- compute root for special cases
		if X = 0.0 then
			return 0.0;
		elsif ( X = 1.0 ) then
			return 1.0;
		else
			if X = -1.0 then
				return -1.0;
			end if;
		end if;

		-- compute root for general cases
		if negative then
			xlocal := -X;
		end if;
		
		oldval := inival;
		newval := (xlocal/(oldval*oldval) + 2.0*oldval)/3.0;

		while ( abs(newval -oldval) > relative_err ) loop
			oldval := newval;
			newval :=(xlocal/(oldval*oldval) + 2.0*oldval)/3.0;
		end loop;

		if negative then
			newval := -newval;
		end if;

		return newval;

    end CBRT;

    function "**" (X : integer; Y : real) return real is
    	-- returns Y power of X ==>  X**Y;
    	-- error if X = 0 and Y <= 0.0
    	-- error if X < 0 and Y does not have an integer value
    begin
	-- check validity of argument
	if ( X = 0  ) and ( Y <= 0.0 ) then
		assert false report "X = 0 and Y <= 0.0 in X**Y" 
			severity ERROR;
		return (0.0);
	end if;

	if ( X < 0  ) and ( Y /= REAL(INTEGER(Y)) ) then
		assert false report "X < 0 and Y \= integer in X**Y" 
			severity ERROR;
		return (0.0);
	end if;

	-- compute the result
	return EXP (Y * LOG (REAL(X)));
    end "**";

    function "**" (X : real; Y : real) return real is
    	-- returns Y power of X ==>  X**Y;
    	-- error if X = 0.0 and Y <= 0.0
    	-- error if X < 0.0 and Y does not have an integer value
    begin
	-- check validity of argument
	if ( X = 0.0  ) and ( Y <= 0.0 ) then
		assert false report "X = 0.0 and Y <= 0.0 in X**Y" 
			severity ERROR;
		return (0.0);
	end if;

	if ( X < 0.0  ) and ( Y /= REAL(INTEGER(Y)) ) then
		assert false report "X < 0.0 and Y \= integer in X**Y" 
			severity ERROR;
		return (0.0);
	end if;

	-- compute the result
	return EXP (Y * LOG (X));
    end "**";

    function EXP  (X : real ) return real is
    	-- returns e**X; where e = MATH_E
  	--
  	-- This function computes the exponential using the following series:
  	--    exp(x) = 1 + x + x**2/2! + x**3/3! + ... ; x > 0
  	--
	constant eps : real := 0.000001;	-- precision criteria

    	variable reciprocal: boolean := x < 0.0;-- check sign of argument
    	variable xlocal : real := abs(x);       -- use positive value
    	variable oldval: real ;			-- following variables are
    	variable num: real ;			-- used for series evaluation
    	variable count: integer ;
    	variable denom: real ;
    	variable newval: real ;

     begin
    	-- compute value for special cases
	if X = 0.0 then
		return 1.0;
	else
		if  X = 1.0  then
			return MATH_E;
		end if;
	end if;

    	-- compute value for general cases
    	oldval := 1.0;
    	num := xlocal;
    	count := 1;
    	denom := 1.0;
    	newval:= oldval + num/denom;

    	while ( abs(newval - oldval) > eps ) loop
		oldval := newval;
		num := num*xlocal;
        	count := count +1;
		denom := denom*(real(count));
        	newval := oldval + num/denom;
    	end loop;

    	if reciprocal then
        	newval := 1.0/newval;
    	end if;

    	return newval;
     end EXP;


    function LOG (X : real ) return real is
    	-- returns natural logarithm of X; X > 0
	--
  	-- This function computes the exponential using the following series:
  	--    log(x) = 2[ (x-1)/(x+1) + (((x-1)/(x+1))**3)/3.0 + ...] ; x > 0
	--
    	
	constant eps : real := 0.000001;	-- precision criteria

    	variable xlocal: real ;			-- following variables are
    	variable oldval: real ;			-- used to evaluate the series
    	variable xlocalsqr: real ;
	variable factor : real ;
    	variable count: integer ;
    	variable newval: real ;
    	
     begin
    	-- check validity of argument
    	if ( x <= 0.0 ) then
       	  assert false report "X <= 0 in LOG(X)" 
			severity ERROR;
            return(REAL'LOW);
    	end if;

    	-- compute value for special cases
    	if ( X = 1.0 ) then
		return 0.0;
	else
		if ( X = MATH_E ) then
			return 1.0;
		end if;
	end if;

    	-- compute value for general cases
    	xlocal := (X - 1.0)/(X + 1.0);
    	oldval := xlocal;
    	xlocalsqr := xlocal*xlocal;
	factor := xlocal*xlocalsqr; 
    	count := 3;
    	newval := oldval + (factor/real(count));

	while ( abs(newval - oldval) > eps ) loop
		oldval := newval;
        	count := count +2;
		factor := factor * xlocalsqr;
		newval := oldval + factor/real(count);
    	end loop;

	newval := newval * 2.0;
    	return newval;
    end LOG;

    function LOG (BASE: positive; X : real) return real is
    	-- returns logarithm base BASE of X; X > 0
    begin
    	-- check validity of argument
    	if ( BASE <= 0 ) or ( x <= 0.0 ) then
       	  assert false report "BASE <= 0 or X <= 0.0 in LOG(BASE, X)" 
				severity ERROR;
            return(REAL'LOW);
    	end if;

	-- compute the value
	return ( LOG(X)/LOG(REAL(BASE)));
    end LOG;

    function  SIN (X : real ) return real is
    	-- returns sin X; X in radians
        variable n : INTEGER;
    begin 
      if (x < 1.6 ) and (x > -1.6) then
         return    CORDIC( KC, 0.0, x, 27, ROTATION)(1);
      end if; 

      n := INTEGER( x / HALF_PI );
      case QUADRANT( n mod 4 ) is 
         when 0 => 
            return  CORDIC( KC, 0.0, x - REAL(n) * HALF_PI, 27, ROTATION)(1);
         when 1 => 
            return  CORDIC( KC, 0.0, x - REAL(n) * HALF_PI, 27, ROTATION)(0);
         when 2 =>
            return -CORDIC( KC, 0.0, x - REAL(n) * HALF_PI, 27, ROTATION)(1);
         when 3 => 
            return -CORDIC( KC, 0.0, x - REAL(n) * HALF_PI, 27, ROTATION)(0);
       end case;
    end SIN;

   
   function COS (x : REAL) return REAL is 
    	-- returns cos X; X in radians
      variable n : INTEGER;
   begin 
      if (x < 1.6 ) and (x > -1.6) then
         return CORDIC( KC, 0.0, x, 27, ROTATION)(0);
      end if; 

      n := INTEGER( x / HALF_PI );
      case QUADRANT( n mod 4 ) is 
         when 0 => 
            return  CORDIC( KC, 0.0, x - REAL(n) * HALF_PI, 27, ROTATION)(0);
         when 1 => 
            return -CORDIC( KC, 0.0, x - REAL(n) * HALF_PI, 27, ROTATION)(1);
         when 2 =>
            return -CORDIC( KC, 0.0, x - REAL(n) * HALF_PI, 27, ROTATION)(0);
         when 3 => 
            return  CORDIC( KC, 0.0, x - REAL(n) * HALF_PI, 27, ROTATION)(1);
       end case;
   end COS;
   
   function TAN (x : REAL) return REAL is 
    	-- returns tan X; X in radians
    	-- X /= ((2k+1) * PI/2), where k is an integer
      variable n : INTEGER := INTEGER( x / HALF_PI );
      variable v : REAL_ARR_3 :=
                       CORDIC( KC, 0.0, x - REAL(n) * HALF_PI, 27, ROTATION);
   begin
      if n mod 2 = 0 then
         return v(1)/v(0);
      else
         return -v(0)/v(1);
      end if;
   end TAN; 
    
   function ASIN (x : real ) return real is
    	-- returns  -PI/2 < asin X < PI/2; | X | <= 1
   begin   
      if abs x > 1.0 then 
         assert false report "Out of range parameter passed to ASIN" 
			severity ERROR;
         return x;
      elsif abs x < 0.9 then 
         return atan(x/(sqrt(1.0 - x*x)));
      elsif x > 0.0 then 
         return HALF_PI - atan(sqrt(1.0 - x*x)/x);
      else 
         return - HALF_PI + atan((sqrt(1.0 - x*x))/x);
      end if;
   end ASIN; 
   
   function ACOS (x : REAL) return REAL is
    	-- returns  0 < acos X < PI; | X | <= 1
   begin  
      if abs x > 1.0 then 
         assert false report "Out of range parameter passed to ACOS" 
			severity ERROR; 
         return x;
      elsif abs x > 0.9 then
         if x > 0.0 then  
            return atan(sqrt(1.0 - x*x)/x);
         else
            return MATH_PI - atan(sqrt(1.0 - x*x)/x); 
         end if; 
      else 
         return HALF_PI - atan(x/sqrt(1.0 - x*x));
      end if;
   end ACOS; 
   
   function ATAN (x : REAL) return REAL is
    	-- returns  -PI/2 < atan X < PI/2
   begin
      return  CORDIC( 1.0, x, 0.0, 27, VECTORING )(2);      
   end ATAN; 

   function ATAN2 (x : REAL; y : REAL) return REAL is 
    	-- returns  atan (X/Y); -PI < atan2(X,Y) < PI; Y /= 0.0
   begin   
     if y = 0.0 then 
        if x = 0.0 then 
           assert false report "atan2(0.0, 0.0) is undetermined, returned 0,0" 
           	severity NOTE;
           return 0.0; 
        elsif x > 0.0 then 
           return 0.0;
        else 
           return MATH_PI;
        end if;
     elsif x > 0.0 then  
        return  CORDIC( x, y, 0.0, 27, VECTORING )(2); 
     else 
        return MATH_PI + CORDIC( x, y, 0.0, 27, VECTORING )(2); 
     end if;     
   end ATAN2; 


    function SINH (X : real) return real is
    	-- hyperbolic sine; returns (e**X - e**(-X))/2
    begin
		return ( (EXP(X) - EXP(-X))/2.0 );
    end SINH;

    function  COSH (X : real) return real is
    	-- hyperbolic cosine; returns (e**X + e**(-X))/2
    begin
		return ( (EXP(X) + EXP(-X))/2.0 );
    end COSH;

    function  TANH (X : real) return real is
    	-- hyperbolic tangent; -- returns (e**X - e**(-X))/(e**X + e**(-X))
    begin
		return ( (EXP(X) - EXP(-X))/(EXP(X) + EXP(-X)) );
    end TANH;
    
    function ASINH (X : real) return real is
    	-- returns ln( X + sqrt( X**2 + 1))
    begin
		return ( LOG( X + SQRT( X**2 + 1.0)) );
    end ASINH;

    function ACOSH (X : real) return real is
    	-- returns ln( X + sqrt( X**2 - 1));   X >= 1
    begin
      	if abs x >= 1.0 then 
         	assert false report "Out of range parameter passed to ACOSH" 
			severity ERROR; 
         	return x;
      	end if;

	return ( LOG( X + SQRT( X**2 - 1.0)) );
    end ACOSH;

    function ATANH (X : real) return real is
    	-- returns (ln( (1 + X)/(1 - X)))/2 ; | X | < 1
    begin
      	if abs x < 1.0 then 
        	assert false report "Out of range parameter passed to ATANH" 
			severity ERROR; 
        	return x;
      	end if;

	return( LOG( (1.0+X)/(1.0-X) )/2.0 );
    end ATANH;

end  MATH_REAL;



